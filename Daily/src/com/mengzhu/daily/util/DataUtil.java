package com.mengzhu.daily.util;

import java.util.ArrayList;
import java.util.List;

import android.text.format.Time;

import com.mengzhu.daily.entity.Task;
import com.mengzhu.daily.entity.Timed;

/**
 * Created by MGC01 on 7/7/2015.
 */
public class DataUtil {

    public static String[] imageUrls = new String[]{
            "http://cdnweb.b5m.com/web/cmsphp/article/201505/d61be5bde07164d2a5aeb96ec8790bf2.jpg",
            "http://cdnq.duitang.com/uploads/item/201407/07/20140707014508_JxAY2.jpeg",
            "http://www.3761.com/uploads/pic/791378258336.jpg",
            "http://p3.gexing.com/G1/M00/C0/F7/rBACE1Ojp7iDJJzTAAAWMzSlqR0708_200x200_3.jpg?recache=20131108",
            "http://p2.gexing.com/G1/M00/07/E7/rBACE1QMtbeTotShAAAcpr2Zveg734_200x200_3.jpg?recache=20131108",
            "http://p2.gexing.com/touxiang/20120802/0922/5019d66eef7ed_200x200_3.jpg",
            "http://img5q.duitang.com/uploads/item/201506/05/20150605140809_CxZPt.jpeg",
            "http://v1.qzone.cc/avatar/201308/22/10/36/521579394f4bb419.jpg!200x200.jpg",
            "http://www.qqzhi.com/uploadpic/2014-09-05/160840259.jpg",
            "http://f.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=b6ac00c4cbea15ce41bbe80d833016c5/4bed2e738bd4b31c3296fafa85d6277f9f2ff8cd.jpg",
            "http://d.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=5392193b8518367aaddc77d91b43a7e2/bba1cd11728b47105ad129dec0cec3fdfc032322.jpg",
            "http://www.qqzhi.com/uploadpic/2014-11-10/183747998.jpg",
            "http://www.qqbody.com/uploads/allimg/201409/19-173138_524.jpg",

            "http://cdnweb.b5m.com/web/cmsphp/article/201505/d61be5bde07164d2a5aeb96ec8790bf2.jpg",
            "http://cdnq.duitang.com/uploads/item/201407/07/20140707014508_JxAY2.jpeg",
            "http://www.3761.com/uploads/pic/791378258336.jpg",
            "http://p3.gexing.com/G1/M00/C0/F7/rBACE1Ojp7iDJJzTAAAWMzSlqR0708_200x200_3.jpg?recache=20131108",
            "http://p2.gexing.com/G1/M00/07/E7/rBACE1QMtbeTotShAAAcpr2Zveg734_200x200_3.jpg?recache=20131108",
            "http://p2.gexing.com/touxiang/20120802/0922/5019d66eef7ed_200x200_3.jpg",
            "http://img5q.duitang.com/uploads/item/201506/05/20150605140809_CxZPt.jpeg",
            "http://v1.qzone.cc/avatar/201308/22/10/36/521579394f4bb419.jpg!200x200.jpg",
            "http://www.qqzhi.com/uploadpic/2014-09-05/160840259.jpg",
            "http://f.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=b6ac00c4cbea15ce41bbe80d833016c5/4bed2e738bd4b31c3296fafa85d6277f9f2ff8cd.jpg",
            "http://d.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=5392193b8518367aaddc77d91b43a7e2/bba1cd11728b47105ad129dec0cec3fdfc032322.jpg",
            "http://www.qqzhi.com/uploadpic/2014-11-10/183747998.jpg",
            "http://www.qqbody.com/uploads/allimg/201409/19-173138_524.jpg"
    };
    
    public static List<String> getStrs(){
    	List<String> strList = new ArrayList<>();
    	strList.add("<div id='test'><style>.reply{padding: 0 10px 10px 10px;line-height: 26px;font-size: 18px;}"
    			+ "blockquote{background-color: #EEEEEE;position:relative;border-radius: 5px;"
    			+ "font-size: 16px;color: #333;padding: 10px;margin-bottom: 15px;"
    			+ "border-top:1px solid #AFAFAF;border-right:1px solid #AFAFAF;"
    			+ "margin:0 0 10px 0;}"
    			+ "blockquote:after {content: '';border: 10px solid transparent;"
    			+ "border-top-color: #EEEEEE;box-shadow: 1px #000;width: 0;"
    			+ "height: 0;position: absolute;left: 40px;bottom: 0;margin-bottom: -20px}"
    			+ "</style><div class='reply'>"
    			+ "<blockquote>引用第1楼涵四季雪于2015-07-28 16:48发表的&nbsp;&nbsp;:"
    			+ "<br />忧伤的蓝衣小哥 <br /></blockquote><br />有没有电瓶车忧伤</div></div>"
    			+ "<script>"
    			+ "if(document.getElementsByClassName('reply').length != 0) {"
    			+ "ReplyInterface.getContentHeight(3, document.getElementById('reply').scrollHeight - 10);} "
    			+ "else ReplyInterface.getContentHeight(3, document.getElementById('reply').scrollHeight + 6);"
    			+ "</script>");
    	strList.add("<div id='test' style='color:red;'>111111111111111111111111111111111"+"<br/>"
    			+ "111111111111"+"<br/>"
    			+ "111111111111111111111111111111111111111111111"+"<br/>"
    			+ "111111111111111111111111111111111111"+"<br/>"
    					+ "111111111</div>");
    	strList.add("<div id='test'>2222222222222222222222"+"<br/></div>"
    			+ "22222222222222");
    	strList.add("<div id='test'>333333333333333333333333333333333333"+"<br/>"
    			+ "333333333"+"<br/>"
    			+ "3333333333333333333333333333333333333333"+"<br/>"
    			+ "333333"+"<br/>"
    			+ "333333333333333333333333333333"+"<br/>"
    			+ "3333333333333333"+"<br/>"
    			+ "33333333333333333333333333333333333333"+"<br/>"
    			+ "33333333"+"<br/>"
    			+ "333333333333333333333333333333333333</div>");
    	strList.add("<div id='test'>4444444444444444444444444444"+"<br/>"
    			+ "4444444444444444444</div>");
    	strList.add("<div id='test'>5555555555555555</div>");
    	strList.add("<div id='test'>666666666666666666666666</div>");
    	strList.add("<div id='test'>777777777777777"+"<br/>"
    			+ "77777777777777777777777777777777"+"<br/>"
    			+ "77777777777777</div>");
    	strList.add("<div id='test'>888888</div>");
    	strList.add("<div id='test'>9999999999999999"+"<br/>"
    			+ "9999999999999999999</div>");
    	strList.add("<div id='test'>10100101010101001001010"+"<br/>"
    			+ "100110100100100101010010"+"<br/>"
    			+ "101001101010101010100100101"+"<br/>"
    			+ "00101010101010101010"+"<br/>"
    			+ "10</div>");
    	strList.add("<div id='test'>111111111111111111"+"<br/>"
    			+ "1111111111111111111111111</div>");
    	strList.add("<div id='test'>121212121212121221"+"<br/>"
    			+ "212121212121212212121212211"+"<br/>"
    			+ "2212121212212121221"+"<br/>"
    			+ "22121212121"+"<br/>"
    			+ "212121212121212121"+"<br/>"
    			+ "21212121212122121212</div>");
    	strList.add("<div id='test' style='color:red;'>111111111111111111111111111111111"+"<br/>"
    			+ "111111111111"+"<br/>"
    			+ "111111111111111111111111111111111111111111111"+"<br/>"
    			+ "111111111111111111111111111111111111"+"<br/>"
    					+ "111111111</div>");
    	strList.add("<div id='test'>2222222222222222222222"+"<br/>"
    			+ "<img src='http://cdnweb.b5m.com/web/cmsphp/article/201505/d61be5bde07164d2a5aeb96ec8790bf2.jpg'/>"+"<br/>"
    			+ "22222222222222</div>");
    	strList.add("<div id='test'>333333333333333333333333333333333333"+"<br/>"
    			+ "333333333"+"<br/>"
    			+ "3333333333333333333333333333333333333333"+"<br/>"
    			+ "333333"+"<br/>"
    			+ "333333333333333333333333333333"+"<br/>"
    			+ "3333333333333333"+"<br/>"
    			+ "33333333333333333333333333333333333333"+"<br/>"
    			+ "33333333"+"<br/>"
    			+ "333333333333333333333333333333333333</div>");
    	strList.add("<div id='test'>4444444444444444444444444444"+"<br/>"
    			+ "4444444444444444444</div>");
    	strList.add("<div id='test'>5555555555555555</div>");
    	strList.add("<div id='test'>666666666666666666666666</div>");
    	strList.add("<div id='test'>777777777777777"+"<br/>"
    			+ "77777777777777777777777777777777"+"<br/>"
    			+ "77777777777777</div>");
    	strList.add("<div id='test'>888888</div>");
    	strList.add("<div id='test'>9999999999999999"+"<br/>"
    			+ "9999999999999999999</div>");
    	strList.add("<div id='test'>10100101010101001001010"+"<br/>"
    			+ "100110100100100101010010"+"<br/>"
    			+ "101001101010101010100100101"+"<br/>"
    			+ "00101010101010101010"+"<br/>"
    			+ "10</div>");
    	strList.add("<div id='test'>111111111</div>");
    	strList.add("<div id='test'>12121212</div>");
    	return strList;
    }

    public static List<Task> getData(){
        List<Task> messages = new ArrayList<>();
        for (int i = 0; i < 10; i ++) {
            Task message = new Task( "Joselyn-"+i,4,4);
            messages.add(message);
        }

        return messages;
    }
    
    public static List<Timed> getTimeds(){
    	List<Timed> timeds = new ArrayList<>();
    	
    	for (int i = 0; i < 10; i++) {
			Timed timed = new Timed("吃饭", 21222221, 0);
			timeds.add(timed);
		}
    	return timeds;
    }
}
