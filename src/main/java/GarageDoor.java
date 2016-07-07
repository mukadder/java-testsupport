class GarageDoor {

    enum State {CLOSED, OPENING, OPEN, CLOSING, STOPPED_WHILE_CLOSING, STOPPED_WHILE_OPENING}

    public static void main(String[] args) {
        int[] FSMData = new int[] {1, -1, 5, 2, 3, -1, 4, 0, 1, -1, 3, -1};
        State doorState = State.CLOSED;
        java.util.Scanner in = new java.util.Scanner(System.in);
        while (in.hasNext()) {
            int command = in.nextLine().startsWith("b") ? 0 : 1;
            String cMsg = command == 0 ? "> Button clicked." : "> Cycle complete.";
            System.out.printf("Door: %s%n%s%n", doorState, cMsg);
            doorState = doorState.values()[FSMData[doorState.ordinal() * 2 + command]];
        }
    }
}