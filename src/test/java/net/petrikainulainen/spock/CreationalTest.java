package net.petrikainulainen.spock;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.smalaca.enumeration.designpatterns.Creational;

import org.junit.runner.RunWith;

@RunWith(Parameterized.class)
public class CreationalTest {
 
    private Creational designPattern;
    private String description;
     
    public CreationalTest(Creational designPattern, String description) {
        this.designPattern = designPattern;
        this.description = description;
    }
     
    @Parameters
    public static Collection<Object[]> dataProvider() {
        return Arrays.asList(new Object[][] {
            { 
                Creational.ABSTRACT_FACTORY, 
                "Provide an interface for creating families of related or dependent objects without specifying their concrete classes.",
            },
            { 
                Creational.BUILDER, 
                "Separate the construction of a complex object from its representation allowing the same construction process to create various representations.",
            },
            { 
                Creational.FACTORY_METHOD, 
                "Define an interface for creating a single object, but let subclasses decide which class to instantiate.",
            }
        });
    }
     
    @Test
    public void getPatternExplanation()
    {
        assertSame(description, designPattern.describe());
    }
}