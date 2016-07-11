package com.edu.bu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;



public class Main2 {
    public static void main(String[] args) {
        List<Answer> answerList = new ArrayList<>();

        answerList.add(new Answer(1, true));
        answerList.add(new Answer(2, true));
        answerList.add(new Answer(3, null));

        // map with optional answers (i.e. with null)
        Map<Integer, Optional<Boolean>> answerMapWithOptionals = answerList.stream()
                .collect(Collectors.toMap(Answer::getId, Answer::getAnswerAsOptional));

        // map in which null values are removed
        Map<Integer, Boolean> answerMapWithoutNulls = answerList.stream()
                .filter(a -> a.getAnswerAsOptional().isPresent())
                .collect(Collectors.toMap(Answer::getId, Answer::getAnswer));

        // map in which null values are treated as false by default
        Map<Integer, Boolean> answerMapWithDefaults = answerList.stream()
                .collect(Collectors.toMap(a -> a.getId(), a -> a.getAnswerOrDefault(false)));

        System.out.println("With Optional: " + answerMapWithOptionals);
        System.out.println("Without Nulls: " + answerMapWithoutNulls);
        System.out.println("Wit Defaults: " + answerMapWithDefaults);
    }
    private static class Answer {
        private int id;
        private Optional<Boolean> answer;

        Answer() {
        }

        Answer(int id, Boolean answer) {
            this.id = id;
            this.answer = Optional.ofNullable(answer);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        /**
         * Gets the answer which can be a null value. Use {@link #getAnswerAsOptional()} instead.
         *
         * @return the answer which can be a null value
         */
        public Boolean getAnswer() {
            // What should be the default value? If we return null the callers will be at higher risk of having NPE
            return answer.orElse(null);
        }

        /**
         * Gets the optional answer.
         *
         * @return the answer which is contained in {@code Optional}.
         */
        public Optional<Boolean> getAnswerAsOptional() {
            return answer;
        }

		public static <T, K, U> Collector<T, ?, Map<K, U>> toMap(Function<? super T, ? extends K> keyMapper,
				Function<? super T, ? extends U> valueMapper) {
			return Collectors.collectingAndThen(Collectors.toList(), list -> {
				Map<K, U> result = new HashMap<>();
				for (T item : list) {
					K key = keyMapper.apply(item);
					if (result.putIfAbsent(key, valueMapper.apply(item)) != null) {
						throw new IllegalStateException(String.format("Duplicate key %s", key));
					}
				}
				return result;
			});
}
		public static <T, K, U> Collector<T, ?, Map<K, U>> toMapNullFriendly(
		        Function<? super T, ? extends K> keyMapper,
		        Function<? super T, ? extends U> valueMapper) {
		    @SuppressWarnings("unchecked")
		    U none = (U) new Object();
		    return Collectors.collectingAndThen(
		            Collectors.<T, K, U> toMap(keyMapper,
		                    valueMapper.andThen(v -> v == null ? none : v)), map -> {
		                map.replaceAll((k, v) -> v == none ? null : v);
		                return map;
		            });
		}


        /**
         * Gets the answer or the supplied default value.
         *
         * @return the answer or the supplied default value.
         */
        public boolean getAnswerOrDefault(boolean defaultValue) {
            return answer.orElse(defaultValue);
        }

        public void setAnswer(Boolean answer) {
            this.answer = Optional.ofNullable(answer);
        }
    }
    
}