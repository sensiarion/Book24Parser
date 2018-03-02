import java.io.IOException;

public class Main {
    public static void main(String args[]) throws IOException {
        String post = Book24Parser.getEntity("https://book24.ru/product/istoriya-rossiyskogo-gosudarstva-tsar-petr-alekseevich-aziatskaya-evropeizatsiya-1747176/?block=readers_choice");

        VkSender.post(post,"",0);

    }
}
