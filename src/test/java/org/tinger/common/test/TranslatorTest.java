package org.tinger.common.test;

import org.tinger.common.codec.TingerTranslator;
import org.tinger.common.serialize.impl.JackJsonSerializer;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by tinger on 2022-11-14
 */
public class TranslatorTest {
    public static void main(String[] args) {
        Test test = new Test();
        test.id = UUID.randomUUID().toString();
        test.username = "risesun";
        test.password = "123456";
        test.avatar = "https://unmc.bj.bcebos.com/1612197075961_1867327143.jpg";
        test.gender = Gender.MAN;
        test.birthday = new Date();
        test.nickname = "娃哈哈";
        test.summary = "一位女孩的母亲写给女儿的一封信，刷爆了朋友圈：女儿，听说你要带男朋友回家了。我跟你父亲很开心。但是上周跟他接触之后呢，我们却坚决不同意，我们知道，你们在来往，你很伤心，也很生气，哭着说我们都是为了钱，但是比起来你一生的幸福来说，妈妈还是你能够想清楚，那个男孩并非良人，因为他太穷了。他穷得不仅仅是金钱，而是精神上的匮乏，恋爱很幸福，但是生活的财米油盐里，你一定因为他的穷而吃苦。";
        TingerTranslator translator = new TingerTranslator();
        byte[] bytes = translator.encode(test);
        Object decode = translator.decode(bytes);
        System.out.println(new JackJsonSerializer().toJson(decode));

        bytes = translator.encode(test.summary);
        decode = translator.decode(bytes);
        System.out.println(decode);
    }


    public static class Test implements Serializable {
        private String id;
        private String username;
        private String password;
        private Date birthday;
        private String nickname;
        private Gender gender;
        private String summary;
        private String avatar;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Date getBirthday() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public Gender getGender() {
            return gender;
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

    public enum Gender {
        MAN, FEMALE
    }
}
