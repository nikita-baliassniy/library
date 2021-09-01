class Counter1 {

    aInternal = 0
    aListener = function(val) {}

    set(val) {
        this.aListener(val);
    }

    get a() {
        return this.aInternal;
    }

    registerListener = function(listener) {
        this.aListener = listener;
    }
}