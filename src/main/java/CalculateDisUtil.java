import com.alibaba.fastjson.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class CalculateDisUtil {

    //origin:出发地地名，如:余杭区
    //destination:目的地地名，如:黄浦区
    //返回两地的行车距离，如:234.56公里
    public static double distance(String origin, String destination){
        Map<String, String> params = new HashMap<String, String>();

        String originDouble = HttpClientUtil.doGet("http://api.map.baidu.com/geocoder/v2/?output=json&ak=yzl27a4yvGIOdZCNi0hGhLTy3l6lPrem&address=" + origin);
        String desDouble = HttpClientUtil.doGet("http://api.map.baidu.com/geocoder/v2/?output=json&ak=yzl27a4yvGIOdZCNi0hGhLTy3l6lPrem&address=" + destination);

        com.alibaba.fastjson.JSONObject jsonObjectOri = com.alibaba.fastjson.JSONObject.parseObject(originDouble);
        com.alibaba.fastjson.JSONObject jsonObjectDes = com.alibaba.fastjson.JSONObject.parseObject(desDouble);
        String oriLng = jsonObjectOri.getJSONObject("result").getJSONObject("location").getString("lng");
        String oriLat = jsonObjectOri.getJSONObject("result").getJSONObject("location").getString("lat");

        //System.out.println(oriLat+"|"+oriLng);
        String desLng = jsonObjectDes.getJSONObject("result").getJSONObject("location").getString("lng");
        String desLat = jsonObjectDes.getJSONObject("result").getJSONObject("location").getString("lat");
        //System.out.println(desLat+"|"+desLng);
        params.put("output","json");
        params.put("ak","yzl27a4yvGIOdZCNi0hGhLTy3l6lPrem");
        params.put("origins",oriLat+","+oriLng+"|"+oriLat+","+oriLng);
        params.put("destinations",desLat+","+desLng+"|"+desLat+","+desLng);

        String result = HttpClientUtil.doGet("http://api.map.baidu.com/routematrix/v2/driving", params);
        JSONArray jsonArray = com.alibaba.fastjson.JSONObject.parseObject(result).getJSONArray("result");
        try {
            String distanceString = jsonArray.getJSONObject(0).getJSONObject("distance").getString("text");
            double distanceDouble = Double.parseDouble(distanceString.replace("公里",""));
            return distanceDouble;
        }catch (Exception ex){
            try {
                Thread.sleep(5000);
                distance(origin,destination);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return -1;
    }

    public static void main(String[] args) {
        double distance=CalculateDisUtil.distance("龙岩市小池镇","厦门市");
        System.out.println(distance);
    }

}


