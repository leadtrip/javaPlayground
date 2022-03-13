package wood.mike;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import wood.mike.reactive.ReactorTesting;

public class ReactorTest {

    ReactorTesting reactorTesting = new ReactorTesting();

    @Test
    private void test1() throws InterruptedException {
        StepVerifier.create( reactorTesting.publishAndConsume().log() )
                .expectNext(1, 2, 3)
                .verifyComplete();
    }
}
