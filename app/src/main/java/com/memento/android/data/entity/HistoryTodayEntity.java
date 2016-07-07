package com.memento.android.data.entity;

import java.util.List;

/**
 * 作者: caorh@dxyer.com
 * 日期: 2016-05-06
 * 时间: 17:01
 * 描述：
 * 修改历史：
 */
public class HistoryTodayEntity {

    /**
     * error_code : 0
     * reason : 请求成功！
     * result : [{"_id":"17110507","day":7,"des":"在305年前的今天，1711年5月7日 (农历三月二十)，英国哲学家休谟诞生。","lunar":"","month":5,"pic":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/201005/6/27221239206.jpg","title":"英国哲学家休谟诞生","year":1711},{"_id":"18330507","day":7,"des":"在183年前的今天，1833年5月7日 (农历三月十八)，德国古典主义作曲家约翰内斯·勃拉姆斯诞生。","lunar":"","month":5,"pic":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/201005/6/56221531213.gif","title":"德国古典主义作曲家约翰内斯·勃拉姆斯诞生","year":1833},{"_id":"18400507","day":7,"des":"在176年前的今天，1840年5月7日 (农历四月初六)，俄国作曲家柴可夫斯基诞辰。","lunar":"","month":5,"pic":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/200405/7/D1234414387.jpg","title":"俄国作曲家柴可夫斯基诞辰","year":1840},{"_id":"18610507","day":7,"des":"在155年前的今天，1861年5月7日 (农历三月廿八)，印度诗人泰戈尔诞辰。","lunar":"","month":5,"pic":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/200905/17/6F161826424.jpg","title":"印度诗人泰戈尔诞辰","year":1861},{"_id":"19010507","day":7,"des":"在115年前的今天，1901年5月7日 (农历三月十九)，美国影星贾莱·古柏出生。","lunar":"","month":5,"pic":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/200905/17/3C161736347.jpg","title":"美国影星贾莱·古柏出生","year":1901},{"_id":"19090507","day":7,"des":"在107年前的今天，1909年5月7日 (农历三月十八)，美国著名的发明家兰德·埃德温·赫伯特的诞生。","lunar":"","month":5,"pic":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/201005/6/4622183341.jpg","title":"美国著名的发明家兰德·埃德温·赫伯特的诞生","year":1909},{"_id":"19120507","day":7,"des":"在104年前的今天，1912年5月7日 (农历三月廿一)，小提琴家、作曲家、音乐教育家马思聪诞生。","lunar":"","month":5,"pic":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/201005/6/1D22234986.jpg","title":"小提琴家、作曲家、音乐教育家马思聪诞生","year":1912},{"_id":"19160507","day":7,"des":"在100年前的今天，1916年5月7日 (农历四月初六)，护国军政府设立军务院。","lunar":"","month":5,"pic":"","title":"护国军政府设立军务院","year":1916},{"_id":"19350507","day":7,"des":"在81年前的今天，1935年5月7日 (农历四月初五)，日本向佳木斯武装移民。","lunar":"","month":5,"pic":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/200905/17/6416101182.jpg","title":"日本向佳木斯武装移民","year":1935},{"_id":"19370507","day":7,"des":"在79年前的今天，1937年5月7日 (农历三月廿七)，毛泽东、刘少奇作报告为实现抗日民族统一战线而努力。","lunar":"","month":5,"pic":"","title":"毛泽东、刘少奇作报告为实现抗日民族统一战线而努力","year":1937},{"_id":"19450507","day":7,"des":"在71年前的今天，1945年5月7日 (农历三月廿六)，法西斯德国无条件投降，二战结束。","lunar":"","month":5,"pic":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/200906/3/BB222628629.jpg","title":"法西斯德国无条件投降，二战结束","year":1945},{"_id":"19460507","day":7,"des":"在70年前的今天，1946年5月7日 (农历四月初七)，英国和法国从叙利亚撤军。","lunar":"","month":5,"pic":"","title":"英国和法国从叙利亚撤军","year":1946},{"_id":"19480507","day":7,"des":"在68年前的今天，1948年5月7日 (农历三月廿九)，欧洲统一运动首届大会开幕。","lunar":"","month":5,"pic":"","title":"欧洲统一运动首届大会开幕","year":1948},{"_id":"19490507","day":7,"des":"1949年5月7日 (农历四月初十)，\u201c永不消逝的电波\u201d的主人李白牺牲。","lunar":"","month":5,"pic":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/201005/3/E9233311223.jpg","title":"\u201c永不消逝的电波\u201d的主人李白牺牲","year":1949},{"_id":"19520507","day":7,"des":"1952年5月7日 (农历四月十四)，中国抗议美国残杀战俘。","lunar":"","month":5,"pic":"","title":"中国抗议美国残杀战俘","year":1952},{"_id":"19540507","day":7,"des":"1954年5月7日 (农历四月初五)，奠边府战役胜利。","lunar":"","month":5,"pic":"","title":"奠边府战役胜利","year":1954},{"_id":"19600507","day":7,"des":"1960年5月7日 (农历四月十二)，中央大刀阔斧调整国民经济。","lunar":"","month":5,"pic":"","title":"中央大刀阔斧调整国民经济","year":1960},{"_id":"19610507","day":7,"des":"1961年5月7日 (农历三月廿三)，毛泽东批转周恩来关于农村政策问题的调查报告。","lunar":"","month":5,"pic":"","title":"毛泽东批转周恩来关于农村政策问题的调查报告","year":1961},{"_id":"19660507","day":7,"des":"1966年5月7日 (农历闰三月十七)，毛泽东给林彪写了《五七指示》。","lunar":"","month":5,"pic":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/200905/17/53155456816.jpg","title":"毛泽东给林彪写了《五七指示》","year":1966},{"_id":"19670507","day":7,"des":"1967年5月7日 (农历三月廿八)，现代散文家周作人病逝。","lunar":"","month":5,"pic":"","title":"现代散文家周作人病逝","year":1967},{"_id":"19670507m1","day":7,"des":"1967年5月7日 (农历三月廿八)，我军先后击落26架美军飞机。","lunar":"","month":5,"pic":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/200905/17/5C155429874.jpg","title":"我军先后击落26架美军飞机","year":1967},{"_id":"19680507","day":7,"des":"1968年5月7日 (农历四月十一)，中国社会学家吴景超逝世。","lunar":"","month":5,"pic":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/201005/6/6D222649881.jpg","title":"中国社会学家吴景超逝世","year":1968},{"_id":"19730507","day":7,"des":"1973年5月7日 (农历四月初五)，《华盛顿邮报》因披露水门事件而钦誉全国。","lunar":"","month":5,"pic":"","title":"《华盛顿邮报》因披露水门事件而钦誉全国","year":1973},{"_id":"19750507","day":7,"des":"1975年5月7日 (农历三月廿六)，美国总统福特宣布停止支持越南战争。","lunar":"","month":5,"pic":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/200905/17/30155211885.jpg","title":"美国总统福特宣布停止支持越南战争","year":1975},{"_id":"19780507","day":7,"des":"1978年5月7日 (农历四月初一)，苏联英雄卓娅和舒拉的母亲逝世。","lunar":"","month":5,"pic":"","title":"苏联英雄卓娅和舒拉的母亲逝世","year":1978},{"_id":"19810507","day":7,"des":"1981年5月7日 (农历四月初四)，国民党高级将领杜聿明逝世。","lunar":"","month":5,"pic":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/201103/17/3E122935716.jpg","title":"国民党高级将领杜聿明逝世","year":1981},{"_id":"19840507","day":7,"des":"1984年5月7日 (农历四月初七)，国务院发布《居民身份证试行条例》。","lunar":"","month":5,"pic":"","title":"国务院发布《居民身份证试行条例》","year":1984},{"_id":"19870507","day":7,"des":"1987年5月7日 (农历四月初十)，中信实业银行在北京成立并开始营业。","lunar":"","month":5,"pic":"","title":"中信实业银行在北京成立并开始营业","year":1987},{"_id":"19900507","day":7,"des":"1990年5月7日 (农历四月十三)，苏公布卫国战争中军民损失数字。","lunar":"","month":5,"pic":"","title":"苏公布卫国战争中军民损失数字","year":1990},{"_id":"19900507m1","day":7,"des":"1990年5月7日 (农历四月十三)，中苏美和平登山队登上珠穆朗玛峰。","lunar":"","month":5,"pic":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/200905/17/DA15478530.jpg","title":"中苏美和平登山队登上珠穆朗玛峰","year":1990},{"_id":"19950507","day":7,"des":"1995年5月7日 (农历四月初八)，希拉克再次当选法国总统。","lunar":"","month":5,"pic":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/200905/17/67154415553.jpg","title":"希拉克再次当选法国总统","year":1995},{"_id":"19970507","day":7,"des":"1997年5月7日 (农历四月初一)，光明工程开始实施。","lunar":"","month":5,"pic":"","title":"光明工程开始实施","year":1997},{"_id":"20000507","day":7,"des":"2000年5月7日 (农历四月初四)，普京就任俄总统。","lunar":"","month":5,"pic":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/200405/7/9223464443.jpg","title":"普京就任俄总统","year":2000},{"_id":"20020507","day":7,"des":"2002年5月7日 (农历三月廿五)，北方航空公司一客机在大连湾海域坠毁 112人遇难。","lunar":"","month":5,"pic":"","title":"北方航空公司一客机在大连湾海域坠毁 112人遇难","year":2002},{"_id":"20080507","day":7,"des":"2008年5月7日 (农历四月初三)，中日签署关于全面推进战略互惠关系的联合声明。","lunar":"","month":5,"pic":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/201005/6/1A221057331.jpg","title":"中日签署关于全面推进战略互惠关系的联合声明","year":2008},{"_id":"9730507","day":7,"des":"在1043年前的今天，0973年5月7日 (农历四月初二)，神圣罗马帝国奥托大帝逝世。","lunar":"","month":5,"pic":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/201108/4/1E05728864.jpg","title":"神圣罗马帝国奥托大帝逝世","year":973}]
     */

    private int error_code;
    private String reason;
    /**
     * _id : 17110507
     * day : 7
     * des : 在305年前的今天，1711年5月7日 (农历三月二十)，英国哲学家休谟诞生。
     * lunar :
     * month : 5
     * pic : http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/201005/6/27221239206.jpg
     * title : 英国哲学家休谟诞生
     * year : 1711
     */

    private List<ResultEntity> result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<ResultEntity> getResult() {
        return result;
    }

    public void setResult(List<ResultEntity> result) {
        this.result = result;
    }

    public static class ResultEntity {
        private String _id;
        private int day;
        private String des;
        private String lunar;
        private int month;
        private String pic;
        private String title;
        private int year;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getLunar() {
            return lunar;
        }

        public void setLunar(String lunar) {
            this.lunar = lunar;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }
}