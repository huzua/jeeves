package com.cherry.jeeves.utils.Alimama;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HttpClientDemo {

    /**
     * 获取重定向之后的网址信息
     * @see HttpClient缺省会自动处理客户端重定向
     * @see 即访问网页A后,假设被重定向到了B网页,那么HttpClient将自动返回B网页的内容
     * @see 若想取得B网页的地址,就需要借助HttpContext对象,HttpContext实际上是客户端用来在多次请求响应的交互中,保持状态信息的
     * @see 我们自己也可以利用HttpContext来存放一些我们需要的信息,以便下次请求的时候能够取出这些信息来使用
     */
    public static void main(String[] args){
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext httpContext = new BasicHttpContext();
        //HttpGet httpGet = new HttpGet("https://a.m.taobao.com/i563714158486.htm?price=38-48&sourceType=item&sourceType=item&suid=d21f54b4-85ed-48c2-8344-919fb3102699&ut_sk=1.Wbe5PTN%2B43IDACRqsIzTrn2O_21646297_1545572218466.Copy.1&un=df2892478bfc0e083911dc70f8aca8ac&share_crt_v=1&sp_tk=77+lcDFMR2JvbHczbWjvv6U=&cpp=1&shareurl=true&spm=a313p.22.1aa.999139676699&short_name=h.3KtGGRT");
       // HttpGet httpGet = new HttpGet("https://detail.tmall.com/item.htm?id=563714158486&price=38-48&price=38-48&sourceType=item&sourceType=item&sourceType=item&sourceType=item&suid=d21f54b4-85ed-48c2-8344-919fb3102699&suid=d21f54b4-85ed-48c2-8344-919fb3102699&ut_sk=1.Wbe5PTN+43IDACRqsIzTrn2O_21646297_1545572218466.Copy.1&ut_sk=1.Wbe5PTN+43IDACRqsIzTrn2O_21646297_1545572218466.Copy.1&un=df2892478bfc0e083911dc70f8aca8ac&un=df2892478bfc0e083911dc70f8aca8ac&share_crt_v=1&share_crt_v=1&sp_tk=77%20lcDFMR2JvbHczbWjvv6U=&sp_tk=77%20lcDFMR2JvbHczbWjvv6U=&cpp=1&cpp=1&shareurl=true&shareurl=true&spm=a313p.22.1aa.999139676699&spm=a313p.22.1aa.999139676699&short_name=h.3KtGGRT&short_name=h.3KtGGRT");
        //HttpGet httpGet = new HttpGet("http://item.taobao.com/item.htm?id=563714158486&price=38-48&sourceType=item&sourceType=item&suid=d21f54b4-85ed-48c2-8344-919fb3102699&ut_sk=1.Wbe5PTN%2B43IDACRqsIzTrn2O_21646297_1545572218466.Copy.1&un=df2892478bfc0e083911dc70f8aca8ac&share_crt_v=1&sp_tk=77+lcDFMR2JvbHczbWjvv6U=&cpp=1&shareurl=true&spm=a313p.22.1aa.999139676699&short_name=h.3KtGGRT");
        HttpGet httpGet = new HttpGet("https://a.m.taobao.com/i563714158486.htm?price=38-48&sourceType=item&sourceType=item&suid=d21f54b4-85ed-48c2-8344-919fb3102699&ut_sk=1.Wbe5PTN%2B43IDACRqsIzTrn2O_21646297_1545572218466.Copy.1&un=df2892478bfc0e083911dc70f8aca8ac&share_crt_v=1&sp_tk=77+lcDFMR2JvbHczbWjvv6U=&cpp=1&shareurl=true&spm=a313p.22.1aa.999139676699&short_name=h.3KtGGRT");
        try {
            HttpParams params = new BasicHttpParams();
            params.setParameter("http.protocol.handle-redirects", false); // 默认不让重定向
            params.setParameter("cookie","cna=m/sCFAFYXnUCAXOrPep/gomP; undefined_yxjh-filter-1=true; 122862637_yxjh-filter-1=true; account-path-guide-s1=true; t=83218a6bcaeb2b18b6f9ab8ebf54111e; cookie2=1acb1678ed1eb2a1c2de46effce94d16; v=0; _tb_token_=e443e791757ad; alimamapwag=TW96aWxsYS81LjAgKFdpbmRvd3MgTlQgMTAuMDsgV2luNjQ7IHg2NCkgQXBwbGVXZWJLaXQvNTM3LjM2IChLSFRNTCwgbGlrZSBHZWNrbykgQ2hyb21lLzU4LjAuMzAyOS4xMTAgU2FmYXJpLzUzNy4zNg%3D%3D; cookie32=a0a52b1d30ec94b8d4f21c8bb38cb415; alimamapw=E3QFRHcHFCYDQHEGUFMBVFMHBgE8UQBVBVJXUgABAFYHWFBUUAdWUgICAgYHUgZXVlJUBAA%3D; cookie31=MTIyODYyNjM3LCVFOCU5MyU5RCVFOCVCMCU4MzIwMTUxMDA2LDEyMDc5MzA2ODNAcXEuY29tLFRC; login=URm48syIIVrSKA%3D%3D; JSESSIONID=3D3837611100F441797BB46BEA7E7F7E; apushc959b9f7e6ba270304159d181db2e169=%7B%22ts%22%3A1545578580322%2C%22parentId%22%3A1545578576164%7D; l=aBeR_bk0yFEsF32ByMaYQstFl70jmB5zf_1E1Mam5TEhNaTLx1kEzWroNMsko_xW7U1NuoutI-a..; isg=BDU17JnYheuLQuaCmsdsCFOdRLEvGi49G3D3prdalqz7jlGAfwBQlBlI3BgdyAF8");

            // 这样就能拿到Location头了

            httpGet.setParams(params);

            //将HttpContext对象作为参数传给execute()方法,则HttpClient会把请求响应交互过程中的状态信息存储在HttpContext中
            HttpResponse response = httpClient.execute(httpGet, httpContext);
            System.out.println(response.getAllHeaders());
            //获取重定向之后的主机地址信息,即"http://127.0.0.1:8088"
            HttpHost targetHost = (HttpHost)httpContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
            //获取实际的请求对象的URI,即重定向之后的"/blog/admin/login.jsp"
            HttpUriRequest realRequest = (HttpUriRequest)httpContext.getAttribute(ExecutionContext.HTTP_REQUEST);
            System.out.println("主机地址:" + targetHost);
            System.out.println("URI信息:" + realRequest.getURI());
            HttpEntity entity = response.getEntity();
            Header header = response.getFirstHeader("Location");
            System.out.println(header.getValue());
            for (Header h:response.getAllHeaders()
                 ) {
                System.out.println(h.getName()+"-----------"+h.getValue());
            }
            if(null != entity){
                System.out.println("响应内容:" + EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset()));
                EntityUtils.consume(entity);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            httpClient.getConnectionManager().shutdown();
        }
    }
}
