package yanyan.com.tochar.beans;

import org.json.JSONObject;

import java.util.Date;

public class AppInfo extends JSONObject {
    //是否弹出公告
    private String ispopup;
    //公告
    private String notice;
    //更新说明
    private String updateNotic;
    //更新链接
    private String updateUrl;
    //版本名
    private String versionNm;
    //版本号
    private int version;
    //更新时间
//    private Date updateTm;
//    private Date newTm;



    public String getIspopup() {

        return ispopup;
    }

    public void setIspopup(String ispopup) {
        this.ispopup = ispopup;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getUpdateNotic() {
        return updateNotic;
    }

    public void setUpdateNotic(String updateNotic) {
        this.updateNotic = updateNotic;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public String getVersionNm() {
        return versionNm;
    }

    public void setVersionNm(String versionNm) {
        this.versionNm = versionNm;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "ispopup='" + ispopup + '\'' +
                ", notice='" + notice + '\'' +
                ", updateNotic='" + updateNotic + '\'' +
                ", updateUrl='" + updateUrl + '\'' +
                ", versionNm='" + versionNm + '\'' +
                ", version=" + version +
                '}';
    }
}
