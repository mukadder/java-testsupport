/*
 * Copyright 2009 David Linsin
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.linsin.sample.reflectionjunit.domain;

import org.joda.time.DateMidnight;
import org.joda.time.Years;

import java.math.BigDecimal;

/**
 * Class representing a contract, which belongs to a customer
 *
 * @author David Linsin
 * @version 0.0.1
 * @since 0.0.1
 */
public class Contract {
    private static final Double RATE = 0.3;
    private final Long contractId;
    private BigDecimal quote;
    private Customer owner;

    public Contract(Long argContractId) {
        contractId = argContractId;
        quote = BigDecimal.ZERO;
    }

    public BigDecimal getQuote() {
        return quote;
    }

    public Customer getOwner() {
        return owner;
    }

    /**
     * Sets owner of the contract and updates quote, based on Customer.getBirthday 
     */
    /** package */void setOwner(Customer argOwner) {
        owner = argOwner;
        quote = calculateQuote(argOwner.getBirthday());
    }

    private BigDecimal calculateQuote(DateMidnight argBirtday) {
        Years ageInYears = Years.yearsBetween(argBirtday, new DateMidnight());
        return new BigDecimal(RATE).multiply(new BigDecimal(ageInYears.getYears()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contract contract = (Contract) o;

        if (contractId != null ? !contractId.equals(contract.contractId) : contract.contractId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = contractId != null ? contractId.hashCode() : 0;
        result = 31 * result + (quote != null ? quote.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        return result;
    }
}