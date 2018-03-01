import java.io.IOException;

public class Main {
    public static void main(String args[]) throws IOException {
        String post = Book24Parser.getEntity("https://book24.ru/product/istoriya-rossiyskogo-gosudarstva-tsar-petr-alekseevich-aziatskaya-evropeizatsiya-1747176/?block=readers_choice");

        VkSender.post(post,"https://book24.ru/upload/resize_cache/iblock/548/600_800_1/548851a5b3dd365bd70a40a82ed1dcb3.png",0);
    }
}
