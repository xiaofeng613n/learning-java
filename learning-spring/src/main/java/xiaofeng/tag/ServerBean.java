package xiaofeng.tag;

/**
 * Created by xiao on 2019/1/2.
 */
public class ServerBean {

	private String id;
	private String serverName;

	public void init() {
		System.out.println("serverBean init.");
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}


	@Override
	public String toString() {
		return "id:" +  this.id + " serverName:" + this.serverName;
	}
}
