import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;

public class GeneradorDeArchivo {

    public void guardarJson(Moneda tasas) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter escritura = new FileWriter(tasas.base_code() + "tipo de cambio.json");
        escritura.write(gson.toJson(tasas));
        escritura.close();
    }
}


