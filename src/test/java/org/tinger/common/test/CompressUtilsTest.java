package org.tinger.common.test;

import org.tinger.common.utils.CodecUtils;
import org.tinger.common.utils.CompressUtils;
import org.tinger.common.utils.StringUtils;

/**
 * Created by tinger on 2022-11-14
 */
public class CompressUtilsTest {
    public static void main(String[] args) {
        zip();
        gzip();
    }

    private static void zip(){
        String str = "一位女孩的母亲写给女儿的一封信，刷爆了朋友圈：女儿，听说你要带男朋友回家了。我跟你父亲很开心。但是上周跟他接触之后呢，我们却坚决不同意，我们知道，你们在来往，你很伤心，也很生气，哭着说我们都是为了钱，但是比起来你一生的幸福来说，妈妈还是你能够想清楚，那个男孩并非良人，因为他太穷了。他穷得不仅仅是金钱，而是精神上的匮乏，恋爱很幸福，但是生活的财米油盐里，你一定因为他的穷而吃苦。";
        byte[] bytes = CodecUtils.getUtf8Bytes(str);
        System.out.println(bytes.length);
        bytes = CompressUtils.zipCompress(bytes);
        System.out.println(bytes.length);
        bytes = CompressUtils.zipDecompress(bytes);
        System.out.println(bytes.length);
        String str1 = CodecUtils.newStringUtf8(bytes);
        System.out.println(StringUtils.equals(str1, str));
    }

    private static void gzip(){
        String str = "一位女孩的母亲写给女儿的一封信，刷爆了朋友圈：女儿，听说你要带男朋友回家了。我跟你父亲很开心。但是上周跟他接触之后呢，我们却坚决不同意，我们知道，你们在来往，你很伤心，也很生气，哭着说我们都是为了钱，但是比起来你一生的幸福来说，妈妈还是你能够想清楚，那个男孩并非良人，因为他太穷了。他穷得不仅仅是金钱，而是精神上的匮乏，恋爱很幸福，但是生活的财米油盐里，你一定因为他的穷而吃苦。";
        byte[] bytes = CodecUtils.getUtf8Bytes(str);
        System.out.println(bytes.length);
        bytes = CompressUtils.gzipCompress(bytes);
        System.out.println(bytes.length);
        bytes = CompressUtils.gzipDecompress(bytes);
        System.out.println(bytes.length);
        String str1 = CodecUtils.newStringUtf8(bytes);
        System.out.println(StringUtils.equals(str1, str));
    }
}
