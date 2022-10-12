import io.restassured.http.ContentType;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class TesteCliente {

    String enderecoApiCliente ="http://localhost:8080/";
    String endpointCliente = "cliente";
    String endpointApagaTodos = "/apagaTodos";
    String listaVazia = "{}";

    @Test
    @DisplayName("Quando pegar todos os clientes sem cadastrar, então a lista deve estar vazia")
    public void pegaTodosClientes(){
        deletaTodosClientes();

        given()
                .contentType(ContentType.JSON)
        .when()
                .get(enderecoApiCliente)
        .then()
                .statusCode(200)
                .assertThat().body(new IsEqual<>(listaVazia));

    }

    @Test
    @DisplayName("Quando cadastrar um cliente, então ele deve estar disponivel no resultado")
    public void cadastraCliente(){

        String clienteParaCadastrar = "{\n" +
                "  \"id\": 1004,\n" +
                "  \"idade\": 33,\n" +
                "  \"nome\": \"Maria Souza\",\n" +
                "  \"risco\": 0\n" +
                "}";

        String respostaEsperada = "{\"1004\":{\"nome\":\"Maria Souza\",\"idade\":33,\"id\":1004,\"risco\":0}}";

        given()
                .contentType(ContentType.JSON)
                .body(clienteParaCadastrar)
        .when()
                .post(enderecoApiCliente+endpointCliente)
        .then()
                .statusCode(201)
                .assertThat().body(containsString(respostaEsperada));


    }

    @Test
    @DisplayName("Quando atualizar um cliente, então ele deve ser atualizado")
    public void atualizaCliente() {

        String clienteParaCadastrar = "{\n" +
                "  \"id\": 1004,\n" +
                "  \"idade\": 33,\n" +
                "  \"nome\": \"Maria Souza\",\n" +
                "  \"risco\": 0\n" +
                "}";

        String clienteAtualizado = "{\n" +
                "  \"id\": 1004,\n" +
                "  \"idade\": 40,\n" +
                "  \"nome\": \"Maria Souza\",\n" +
                "  \"risco\": 0\n" +
                "}";

        String respostaEsperada = "{\"1004\":{\"nome\":\"Maria Souza\",\"idade\":40,\"id\":1004,\"risco\":0}}";

        given()
                .contentType(ContentType.JSON)
                .body(clienteParaCadastrar)
        .when()
                .post(enderecoApiCliente+endpointCliente)
        .then()
                .statusCode(201);


        given()
                .contentType(ContentType.JSON)
                .body(clienteAtualizado)
        .when()
                .put(enderecoApiCliente+endpointCliente)
        .then()
                .statusCode(200)
                .assertThat().body(containsString(respostaEsperada));


    }

    @Test
    @DisplayName("Quando deletar um cliente, então ele deve ser removido com sucesso")
    public void deletaClientePorId(){

        String clienteParaCadastrar = "{\n" +
                "  \"id\": 1004,\n" +
                "  \"idade\": 33,\n" +
                "  \"nome\": \"Maria Souza\",\n" +
                "  \"risco\": 0\n" +
                "}";

        String respostaEsperada = "CLIENTE REMOVIDO: { NOME: Maria Souza, IDADE: 33, ID: 1004 }";

        given()
                .contentType(ContentType.JSON)
                .body(clienteParaCadastrar)
        .when()
                .post(enderecoApiCliente+endpointCliente)
        .then()
                .statusCode(201);

        given()
                .contentType(ContentType.JSON)
        .when()
                .delete(enderecoApiCliente+endpointCliente+"/1004")
        .then()
                .statusCode(200)
                .body(new IsEqual<>(respostaEsperada));

    }

    @Test
    public void deletaTodosClientes(){

        given()
                .contentType(ContentType.JSON)
        .when()
                .delete(enderecoApiCliente+endpointCliente+endpointApagaTodos)
        .then()
                .statusCode(200)
                .body(new IsEqual<>(listaVazia));


    }

    }


