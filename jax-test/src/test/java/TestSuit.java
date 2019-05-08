//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.JUnit4;
//import org.springframework.web.client.RestTemplate;
//import reactor.core.publisher.Flux;
//import reactor.netty.ByteBufFlux;
//import reactor.netty.http.client.HttpClient;
//import reactor.netty.tcp.TcpClient;
//import reactor.netty.tcp.TcpServer;
//
//import java.util.concurrent.CountDownLatch;
//
///**
// * @Author huaili
// * @Date 2019/5/8 14:53
// * @Description TODO
// **/
//@RunWith(JUnit4.class)
//public class TestSuit {
//
//    String url = "http://m.sohu.com";
//    int n =100;
//    @Test
//    public void testTcp(){
////        String str = HttpClient.create().baseUrl("http://www.baidu.com")// Prepares an HTTP client ready for configuration
////               // .port(8920)  // Obtains the server's port and provides it as a port to which this
////                // client should connect
////                .post()               // Specifies that POST method will be used
////                .uri("/")// Specifies the path
////               // .send(ByteBufFlux.fromString(Flux.just("Hello")))  // Sends the request body
////                .send(Flux.empty())  // Sends the request body
////                .responseContent()    // Receives the response body
////                .aggregate()
////                .asString()
////                .log("http-client")
////                .block();
//
////        System.out.println(str);
//
//        HttpClient client = HttpClient.create();
//
//
//        long start = System.currentTimeMillis();
//        for(int i=0;i<n;i++){
//            try {
//                String str = client.baseUrl(url)// Prepares an HTTP client ready for configuration
//                        // .port(8920)  // Obtains the server's port and provides it as a port to which this
//                        // client should connect
//                        .get()
//                        // Specifies that POST method will be used
//                        .uri("/")// Specifies the path
//                        // .send(ByteBufFlux.fromString(Flux.just("Hello")))  // Sends the request body
//                       // .send(Flux.empty())  // Sends the request body
//                        .responseContent()    // Receives the response body
//                        .aggregate()
//                        .asString()
//                        .log("http-client").block();
//                System.out.println(str);
//            }catch (Exception e){
//                e.printStackTrace();
//                System.err.println("i="+i);
//            }
//
//        }
//
//        System.out.println("cost time ="+(System.currentTimeMillis()-start)+"ms");
//        //构建一个Flux，它从头开始只发出一系列计数递增整数。
//        //start - 要发出的第一个整数 ; count - 要发出的递增值的总数，包括第一个值
////        Flux<Integer> ints = Flux.range(2, 4);    //分解步骤1
////        //将消费者订阅到此Flux，它将分别消耗序列中的所有元素，处理错误并对完成做出反应。
////        ints.subscribe(                           //分解步骤2
////                //实现一个消费者(注意无返回结果)
////                i -> System.out.println(i),
////                //实现一个可以调用的错误信号的消费者
////                error -> System.err.println("Error " + error),
////                //实现一个在发送一个完成信号后的回调
////                () -> System.out.println("Done"));
//
////
////        CountDownLatch latch = new CountDownLatch(10);
////
////        TcpServer server = TcpServer.create().port(8111);
////        TcpClient client = TcpClient.create("localhost", 80);
////        client.sta
////        final JsonCodec<Pojo, Pojo> codec = new JsonCodec<Pojo, Pojo>(Pojo.class);
////
//////the client/server are prepared
////        server.start( input ->
////
////                //for each connection echo any incoming data
////
////                //return the write confirm publisher from send
////                // >>> close when the write confirm completed
////
////                input.send(
////
////                        //read incoming data
////                        input
////                                .decode(codec) //transform Buffer into Pojo
////                                .log("serve")
////                                .map(codec)    //transform Pojo into Buffer
////
////                        , 5) //auto-flush every 5 elements
////        ).await();
////
////        client.start( input -> {
////
////            //read 10 replies and close
////            input
////                    .take(10)
////                    .decode(codec)
////                    .log("receive")
////                    .subscribe( data -> latch.countDown() );
////
////            //write data
////            input.send(
////                    Flux.range(1, 10)
////                            .map( it -> new Pojo("test" + it) )
////                            .log("send")
////                            .map(codec)
////            ).subscribe();
////
////            //keep-alive, until 10 data have been read
////            return Mono.never();
////
////        }).await();
////
////        latch.await(10, TimeUnit.SECONDS);
////
////        client.shutdown().await();
////        server.shutdown().await();
//
//    }
//
//    @Test
//    public void testSpringHttoClient(){
//        RestTemplate template = new RestTemplate();
//
//
//        long start = System.currentTimeMillis();
//        for(int i=0;i<n;i++){
//            try {
//               // template.getForEntity("http://www.baidu.com",String.class);
//                System.out.println(template.getForEntity(url,String.class).getBody());
//            }catch (Exception e){
//                e.printStackTrace();
//                System.err.println("i="+i);
//            }
//
//        }
//
//        System.out.println("cost time ="+(System.currentTimeMillis()-start)+"ms");
//    }
//}
