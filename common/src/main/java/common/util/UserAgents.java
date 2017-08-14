package common.util;

/**
 * Created with Administrator
 * DATE:2017/2/16
 * Time:11:18
 */
public class UserAgents {
    private static String[] agents = {
            "Mozilla/5.0 (Linux; U; Android 1.5; de-de; HTC Magic Build/CRB17) AppleWebKit/528.5+ (KHTML, like Gecko) Version/3.1.2 Mobile Safari/525.20.1",
            "Mozilla/5.0 (Linux; U; Android 2.1-update1; en-au; HTC_Desire_A8183 V1.16.841.1 Build/ERE27) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17",
            "Mozilla/5.0 (Linux; U; Android 4.2; en-us; Nexus 10 Build/JVP15I) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30",
            "Mozilla/5.0 (Linux; U; Android 4.2.2; ru-ru; Oysters T7V 3G Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30 bdbrowser_i18n/3.1.3.3",
            "Mozilla/5.0 (Linux; U; Android 4.3; en-us; GT-I9500 Build/JSS15J) AppleWebKit/534.24 (KHTML, like Gecko) FlyFlow/3.1 Version/4.0 Mobile Safari/534.24 T5/2.0",
            "Mozilla/5.0 (Linux; Android 4.4.2; SM-G900FQ Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36 bdbrowser_i18n/4.0.0.4",
            "Mozilla/5.0 (Linux; U; Android 4.2.2; en-us; C2105 Build/15.3.A.1.17) AppleWebKit/534.24 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.24 T5/2.0 bdbrowser/6.4.0.4",
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_2 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8H7 Safari/6533.18.5 bdbrowser/6.4.0.4",
            "Mozilla/5.0 (Linux; U; Android-4.0.3; en-us; Galaxy Nexus Build/IML74K) AppleWebKit/535.7 (KHTML, like Gecko) CrMo/16.0.912.75 Mobile Safari/535.7",
            "Mozilla/5.0 (Linux; U; Android-4.0.3; en-us; Xoom Build/IML77) AppleWebKit/535.7 (KHTML, like Gecko) CrMo/16.0.912.75 Safari/535.7",
            "Mozilla/5.0 (Linux; Android 4.0.4; SGH-I777 Build/Task650 & Ktoonsez AOKP) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19",
            "Mozilla/5.0 (Linux; Android 4.1; Galaxy Nexus Build/JRN84D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19",
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 5_1_1 like Mac OS X; en) AppleWebKit/534.46.0 (KHTML, like Gecko) CriOS/19.0.1084.60 Mobile/9B206 Safari/7534.48.3",
            "Mozilla/5.0 (iPad; U; CPU OS 5_1_1 like Mac OS X; en-us) AppleWebKit/534.46.0 (KHTML, like Gecko) CriOS/19.0.1084.60 Mobile/9B206 Safari/7534.48.3",
            "Mozilla/5.0 (SCH-F859/F859DG12;U;NUCLEUS/2.1;Profile/MIDP-2.1 Configuration/CLDC-1.1;480*800;CTC/2.0) Dolfin/2.0",
            "SAMSUNG-GT-B5310/B5310ACIK1 SHP/VPP/R5 Dolfin/1.5 Nextreaming SMM-MMS/1.2.0 profile/MIDP-2.1 configuration/CLDC-1.1",
            "Mozilla/5.0 (SAMSUNG; SAMSUNG-GT-S8500/S8500XXJF4; U; Bada/1.0; fr-fr) AppleWebKit/533.1 (KHTML, like Gecko) Dolfin/2.0 Mobile WVGA SMM-MMS/1.2.0 OPN-B",
            "SAMSUNG-GT-B5310/B5310ACIK1 SHP/VPP/R5 Dolfin/1.5 Nextreaming SMM-MMS/1.2.0 profile/MIDP-2.1 configuration/CLDC-1.1",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.1b2pre) Gecko/20081015 Fennec/1.0a1",
            "Mozilla/5.0 (X11; U; Linux armv7l; en-US; rv:1.9.2a1pre) Gecko/20090322 Fennec/1.0b2pre",
            "Mozilla/5.0 (Android; Linux armv7l; rv:9.0) Gecko/20111216 Firefox/9.0 Fennec/9.0",
            "Mozilla/5.0 (Android; Mobile; rv:12.0) Gecko/12.0 Firefox/12.0",
            "Mozilla/5.0 (Android; Mobile; rv:14.0) Gecko/14.0 Firefox/14.0",
            "Mozilla/5.0 (Mobile; rv:14.0) Gecko/14.0 Firefox/14.0",
            "Mozilla/5.0 (Mobile; rv:17.0) Gecko/17.0 Firefox/17.0",
            "Mozilla/5.0 (Tablet; rv:18.1) Gecko/18.1 Firefox/18.1",
            "Mozilla/5.0 (Android; Mobile; rv:28.0) Gecko/28.0 Firefox/28.0",
            "Mozilla/5.0 (Android; Tablet; rv:29.0) Gecko/29.0 Firefox/29.0",
            "Mozilla/5.0 (Android; Mobile; rv:40.0) Gecko/40.0 Firefox/40.0",
            "Mozilla/5.0 (iPad; CPU iPhone OS 8_3 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) FxiOS/1.0 Mobile/12F69 Safari/600.1.4",
            "Mozilla/5.0 (Android 4.2.2; Tablet; rv:47.0) Gecko/47.0 Firefox/47.0",
            "MQQBrowser/24 (Linux; U; 2.2.1; zh-cn; GT-S5570 Build/FROYO.JPKA3;240*320)",
            "MQQBrowser/Mini2.2 (Nokia6303iclassic/10.80)",
            "MQQBrowser/26 (Linux; U; 2.3.6; zh-cn; HUAWEI C8650+ Build/C8650+V100R001C92B866SP01;320*480)",
            "Mozilla/5.0 (Linux; U; Android 4.1.1; zh-cn; MI 2S Build/JRO03L) AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0 MQQBrowser/5.0 Mobile Safari/537.36",
            "Mozilla/5.0 (iPhone 5; CPU iPhone OS 7_0_6 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/6.0 MQQBrowser/5.0.5 Mobile/11B651 Safari/8536.25",
            "Mozilla/5.0 (iPad; U; CPU OS 4_2_1 like Mac OS X; ja-jp) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8C148 Safari/6533.18.5",
            "Mozilla/5.0 (iPad; U; CPU OS 3_2 like Mac OS X; es-es) AppleWebKit/531.21.10 (KHTML, like Gecko) Version/4.0.4 Mobile/7B360 Safari/531.21.10",
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_0 like Mac OS X; en-us) AppleWebKit/532.9 (KHTML, like Gecko) Version/4.0.5 Mobile/8A293 Safari/6531.22.7",
            "Mozilla/5.0 (iPad; U; CPU OS 4_3_3 like Mac OS X; de-de) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5",
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_3 like Mac OS X; de-de) AppleWebKit/533.17.9 (KHTML, like Gecko)",
            "NokiaC3-00/5.0 (04.60) Profile/MIDP-2.1 Configuration/CLDC-1.1 Mozilla/5.0 AppleWebKit/420+ (KHTML, like Gecko) Safari/420+",
            "Mozilla/5.0 (iPad; U; CPU OS 4_3_5 like Mac OS X; de-de) AppleWebKit/533.17.9 (KHTML, like Gecko)",
            "Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Mobile/10A5376e",
            "Mozilla/5.0 (iPad; CPU OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53",
            "MobileSafari/9537.53 CFNetwork/672.0.8 Darwin/14.0.0",
            "Mozilla/5.0 (Linux; U; Android 2.3.4; en-US; MT11i Build/4.0.2.A.0.62) AppleWebKit/534.31 (KHTML, like Gecko) UCBrowser/9.0.1.275 U3/0.8.0 Mobile Safari/534.31",
            "UCWEB/8.8 (iPhone; CPU OS_6; en-US)AppleWebKit/534.1 U3/3.0.0 Mobile",
            "Mozilla/5.0 (Linux; U; Android 4.0.4; en-US; Micromax P255 Build/IMM76D) AppleWebKit/534.31 (KHTML, like Gecko) UCBrowser/9.2.0.308 U3/0.8.0 Mobile Safari/534.31",
            "Mozilla/5.0 (Linux; Android 4.4.2; Q510s Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36 Mobile UCBrowser/3.4.1.483",
            "Mozilla/5.0 (iPad; U; CPU OS 9_2 like Mac OS X; en-us; iPad5,3) AppleWebKit/534.46 (KHTML, like Gecko) UCBrowser/2.4.0.367 U3/1 Safari/7543.48.3"
    };

    public static String getRandom() {
        return agents[((int) (Math.random() * (agents.length)))];
    }
}
