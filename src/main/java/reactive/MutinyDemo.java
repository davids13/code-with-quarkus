package reactive;

public class MutinyDemo {

        /*
            Two Core Types
                - Uni<T> – a single item (or failure). Think Future, but reactive
                - Multi<T> – a stream of many items

                Both are lazy and only start when you subscribe

                You can handle events like:
                    - onItem() – do something with the value
                    - onFailure() – retry, log, or recover
                    - onCompletion() – perform cleanup
                    - onSubscribe() – observe lifecycle events
        */

}
