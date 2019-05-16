import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.cloud.gateway.config.HttpClientProperties;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientResponse;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.tcp.ProxyProvider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.security.cert.X509Certificate;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.cloud.gateway.config.HttpClientProperties.Pool.PoolType.DISABLED;
import static org.springframework.cloud.gateway.config.HttpClientProperties.Pool.PoolType.FIXED;

/**
 * @Author huaili
 * @Date 2019/5/8 14:53
 * @Description TODO
 **/
@RunWith(JUnit4.class)
public class TestSuit {

    String url = "http://m.sohu.com/limit";
    int n = 10;
    //@Test
    public void testTcp(){
        try {
            SocketChannel sc = SocketChannel.open();
            FileChannel fc = new FileInputStream("").getChannel();
            fc.tryLock();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        String str = HttpClient.create().baseUrl("http://www.baidu.com")// Prepares an HTTP client ready for configuration
//               // .port(8920)  // Obtains the server's port and provides it as a port to which this
//                // client should connect
//                .post()               // Specifies that POST method will be used
//                .uri("/")// Specifies the path
//               // .send(ByteBufFlux.fromString(Flux.just("Hello")))  // Sends the request body
//                .send(Flux.empty())  // Sends the request body
//                .responseContent()    // Receives the response body
//                .aggregate()
//                .asString()
//                .log("http-client")
//                .block();

//        System.out.println(str);

        HttpClient client = httpClient(new HttpClientProperties());


        long start = System.currentTimeMillis();
        for(int i=0;i<n;i++){
            try {
                Flux<HttpClientResponse> responseFlux = client.baseUrl(url)// Prepares an HTTP client ready for configuration
                        // .port(8920)  // Obtains the server's port and provides it as a port to which this
                        // client should connect
                        .get()
                        // Specifies that POST method will be used
                        .uri("/")// Specifies the path
                        // .send(ByteBufFlux.fromString(Flux.just("Hello")))  // Sends the request body
                       // .send(Flux.empty())  // Sends the request body
                        .responseConnection((res,connection) -> {


                            Mono<String> body = connection.inbound().receive().retain().aggregate().asString();
                            System.out.println(body);
                            return Mono.just(res);});
//                        .responseContent()    // Receives the response body
//                        .aggregate()
//                        .asString()
//                        .log("http-client").block();
                System.out.println(responseFlux.blockFirst());
            }catch (Exception e){
                e.printStackTrace();
                System.err.println("i="+i);
            }

        }

        System.out.println("cost time ="+(System.currentTimeMillis()-start)+"ms");
        //构建一个Flux，它从头开始只发出一系列计数递增整数。
        //start - 要发出的第一个整数 ; count - 要发出的递增值的总数，包括第一个值
//        Flux<Integer> ints = Flux.range(2, 4);    //分解步骤1
//        //将消费者订阅到此Flux，它将分别消耗序列中的所有元素，处理错误并对完成做出反应。
//        ints.subscribe(                           //分解步骤2
//                //实现一个消费者(注意无返回结果)
//                i -> System.out.println(i),
//                //实现一个可以调用的错误信号的消费者
//                error -> System.err.println("Error " + error),
//                //实现一个在发送一个完成信号后的回调
//                () -> System.out.println("Done"));

//
//        CountDownLatch latch = new CountDownLatch(10);
//
//        TcpServer server = TcpServer.create().port(8111);
//        TcpClient client = TcpClient.create("localhost", 80);
//        client.sta
//        final JsonCodec<Pojo, Pojo> codec = new JsonCodec<Pojo, Pojo>(Pojo.class);
//
////the client/server are prepared
//        server.start( input ->
//
//                //for each connection echo any incoming data
//
//                //return the write confirm publisher from send
//                // >>> close when the write confirm completed
//
//                input.send(
//
//                        //read incoming data
//                        input
//                                .decode(codec) //transform Buffer into Pojo
//                                .log("serve")
//                                .map(codec)    //transform Pojo into Buffer
//
//                        , 5) //auto-flush every 5 elements
//        ).await();
//
//        client.start( input -> {
//
//            //read 10 replies and close
//            input
//                    .take(10)
//                    .decode(codec)
//                    .log("receive")
//                    .subscribe( data -> latch.countDown() );
//
//            //write data
//            input.send(
//                    Flux.range(1, 10)
//                            .map( it -> new Pojo("test" + it) )
//                            .log("send")
//                            .map(codec)
//            ).subscribe();
//
//            //keep-alive, until 10 data have been read
//            return Mono.never();
//
//        }).await();
//
//        latch.await(10, TimeUnit.SECONDS);
//
//        client.shutdown().await();
//        server.shutdown().await();

    }

    @Test
    public void testSpringHttoClient(){
        RestTemplate template = new RestTemplate();


        long start = System.currentTimeMillis();
        for(int i=0;i<n;i++){
            try {
               // template.getForEntity(url,String.class);
                System.out.println(template.getForEntity(url,String.class).getBody());
            }catch (Exception e){
                e.printStackTrace();
                System.err.println("i="+i);
            }

        }

        System.out.println("RestTemplate cost time ="+(System.currentTimeMillis()-start)+"ms");
    }



    public HttpClient httpClient(HttpClientProperties properties) {

        // configure pool resources
        HttpClientProperties.Pool pool = properties.getPool();

        ConnectionProvider connectionProvider;
        if (pool.getType() == DISABLED) {
            connectionProvider = ConnectionProvider.newConnection();
        }
        else if (pool.getType() == FIXED) {
            connectionProvider = ConnectionProvider.fixed(pool.getName(),
                    pool.getMaxConnections(), pool.getAcquireTimeout());
        }
        else {
            connectionProvider = ConnectionProvider.elastic(pool.getName());
        }

        HttpClient httpClient = HttpClient.create(connectionProvider)
                .tcpConfiguration(tcpClient -> {

                    if (properties.getConnectTimeout() != null) {
                        tcpClient = tcpClient.option(
                                ChannelOption.CONNECT_TIMEOUT_MILLIS,
                                properties.getConnectTimeout());
                    }

                    // configure proxy if proxy host is set.
                    HttpClientProperties.Proxy proxy = properties.getProxy();

                    if (StringUtils.hasText(proxy.getHost())) {

                        tcpClient = tcpClient.proxy(proxySpec -> {
                            ProxyProvider.Builder builder = proxySpec
                                    .type(ProxyProvider.Proxy.HTTP)
                                    .host(proxy.getHost());

                            PropertyMapper map = PropertyMapper.get();

                            map.from(proxy::getPort).whenNonNull().to(builder::port);
                            map.from(proxy::getUsername).whenHasText()
                                    .to(builder::username);
                            map.from(proxy::getPassword).whenHasText()
                                    .to(password -> builder.password(s -> password));
                            map.from(proxy::getNonProxyHostsPattern).whenHasText()
                                    .to(builder::nonProxyHosts);
                        });
                    }
                    return tcpClient;
                });

        HttpClientProperties.Ssl ssl = properties.getSsl();
        if (ssl.getTrustedX509CertificatesForTrustManager().length > 0
                || ssl.isUseInsecureTrustManager()) {
            httpClient = httpClient.secure(sslContextSpec -> {
                // configure ssl
                SslContextBuilder sslContextBuilder = SslContextBuilder.forClient();

                X509Certificate[] trustedX509Certificates = ssl
                        .getTrustedX509CertificatesForTrustManager();
                if (trustedX509Certificates.length > 0) {
                    sslContextBuilder.trustManager(trustedX509Certificates);
                }
                else if (ssl.isUseInsecureTrustManager()) {
                    sslContextBuilder
                            .trustManager(InsecureTrustManagerFactory.INSTANCE);
                }

                sslContextSpec.sslContext(sslContextBuilder)
                        .defaultConfiguration(ssl.getDefaultConfigurationType())
                        .handshakeTimeout(ssl.getHandshakeTimeout())
                        .closeNotifyFlushTimeout(ssl.getCloseNotifyFlushTimeout())
                        .closeNotifyReadTimeout(ssl.getCloseNotifyReadTimeout());
            });
        }

        return httpClient;
    }


    @Test
    public void test3(){


        WebClient webClient = WebClient.create();
        Mono<String> mono = webClient.get().uri(url).retrieve().bodyToMono(String.class);
        mono.subscribe(System.out::println);

        long start = System.currentTimeMillis();
        WebClient client = WebClient.create();
        AtomicInteger sum = new AtomicInteger();
        for(int i=0;i<n;i++) {

            Mono<String> body = client.get()
                    .uri(url)
                    //  .uri("/employees/{id}", "1")
                    .retrieve()
                    .bodyToMono(String.class);

            body.subscribe(s->{
                System.out.println("收到："+s);
                sum.incrementAndGet();
            });

           // body.block();

        }
        while(sum.get()<n){

        }
        long end = System.currentTimeMillis();
        System.out.println("netty cost time = "+(end-start)+"ms");
    }
}
