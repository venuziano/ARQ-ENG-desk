/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apoio;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author User
 */
public class ConsultaWebService
{
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;

    public ConsultaWebService consultaCEP(String cep) throws Exception
    {
        ConsultaWebService endereco = new ConsultaWebService();
        
        try ( CloseableHttpClient httpclient = HttpClients.createDefault();
              CloseableHttpResponse response = httpclient.execute( new HttpGet("https://viacep.com.br/ws/"+cep+"/json/" ) ); )
        {
            HttpEntity entity = response.getEntity();
            String jsonString = EntityUtils.toString( entity );
            JsonObject json = (JsonObject)new JsonParser().parse( jsonString );
            endereco.setLogradouro(json.get( "logradouro" ).getAsString() );
            endereco.setComplemento(json.get( "complemento" ).getAsString() );
            endereco.setBairro(json.get( "bairro" ).getAsString() );
            endereco.setLocalidade(json.get( "localidade" ).getAsString() );
            endereco.setUf(json.get( "uf" ).getAsString() );
        }
        return endereco;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
    
}

