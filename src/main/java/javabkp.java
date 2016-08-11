
private static void ifExists(
    Map<Object, String> map,
    Object key,
    Consumer<String> stringAction
) {
    if (map.containsKey(key)) {
        stringAction.accept(map.get(key));
    }
}

public static void main(String[] args) {
    Map<Object, String> map = ImmutableMap.of("foo", "bar");

    ifExists(map, "foo", str -> { System.out.println(str); });
}