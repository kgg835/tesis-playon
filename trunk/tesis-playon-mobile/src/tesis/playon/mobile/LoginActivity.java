package tesis.playon.mobile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import tesis.playon.mobile.json.model.RolesPorUsuario;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.gson.Gson;

public class LoginActivity extends Activity implements OnClickListener {

    private static final String LOG_TAG = "LoginActivity";

    // String urlUsuario = "http://10.0.2.2:8080/tesis-playon-restful/usuario/gmorales";
    String urlUsuario = "http://10.0.2.2:8080/tesis-playon-restful/rolesporusuario/gmorales";

    @Override
    public void onCreate(Bundle savedInstanceState) {
	Log.d(LOG_TAG, "onCreate");
	super.onCreate(savedInstanceState);
	setContentView(R.layout.login);
	findViewById(R.id.btn_login).setOnClickListener(this);

    }

    @Override
    public void onClick(View arg0) {
	Log.d(LOG_TAG, "onClick");
	Button b = (Button) findViewById(R.id.btn_login);
	b.setClickable(false);
	new LoginService().execute();
    }

    private InputStream retrieveStream(String url) {

	DefaultHttpClient client = new DefaultHttpClient();
	HttpGet getRequest = new HttpGet(url);
	try {
	    HttpResponse getResponse = client.execute(getRequest);
	    final int statusCode = getResponse.getStatusLine().getStatusCode();
	    if (statusCode != HttpStatus.SC_OK) {
		Log.w(getClass().getSimpleName(), "Error " + statusCode + " for URL " + url);
		return null;
	    }
	    HttpEntity getResponseEntity = getResponse.getEntity();
	    return getResponseEntity.getContent();
	} catch (IOException e) {
	    getRequest.abort();
	    Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
	}
	return null;
    }

    class LoginService extends AsyncTask<Void, Void, String> {

	@Override
	protected String doInBackground(Void... params) {

	    InputStream source = retrieveStream(urlUsuario);
	    Gson gson = new Gson();
	    Reader reader = new InputStreamReader(source);
	    RolesPorUsuario response = gson.fromJson(reader, RolesPorUsuario.class);
	    // Usuario response = gson.fromJson(reader, Usuario.class);

	    return response.toString();
	}

	protected void onPostExecute(String results) {
	    Log.d(LOG_TAG, "onPostExecute");
	    if (results != null) {
		Log.d(LOG_TAG, "Results: " + results);
	    }
	    Button b = (Button) findViewById(R.id.btn_login);
	    b.setClickable(true);
	}
    }
}