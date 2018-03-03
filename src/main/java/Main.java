import java.io.IOException;

public class Main {
    public static void main(String args[]) throws IOException {
        String post = Book24Parser.getEntity("https://book24.ru/product/istoriya-rossiyskogo-gosudarstva-tsar-petr-alekseevich-aziatskaya-evropeizatsiya-1747176/?block=readers_choice");
        HttpApacheHandler.getImages("https://book24.ru/upload/resize_cache/iblock/548/600_800_1/548851a5b3dd365bd70a40a82ed1dcb3.png?utm_source=advcake&advcake_params=7wAZ1MM0s5ZAdsH&advcake=1&utm_medium=cpa&utm_campaign=cityads&utm_content=5zcW&utm_term=5zcW");
        VkSender.post(post,"",0);

    }
}
