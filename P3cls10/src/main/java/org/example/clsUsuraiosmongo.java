package org.example;

import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;
import java.util.ArrayList;

public class clsUsuraiosmongo {
    private MongoClient mongoClient;

    private MongoDatabase database;

    private MongoCollection<Document> collection;

    public void conexion(){
        //de conexion contiene la iformacion de la instalaciokn de mongodb
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27817");
        MongoClientSettings settings = Mongo(connectionString)
                .build();
        //crea la conexion y establece la comunicacion
        mongoClient = MongoClients.create(settings);

        //busca la base de datos y coleccion si no existe lo crea
        database = mongoClient.getDatabase("miBaseDatos");
        collection = database.getCollection("usuarios");
    }

    //crear un usuario(insert)
    private void crearUsuario() {
        mdUsuario usuario = new mdUsuario();
        usuario.setNombre("Luis");
        usuario.setCorreo("lgarcial21@gmail.com");
        usuario.setIdTelegram(43850002);

        Document document = new Document("nombre", usuario.getNombre())
                .append("correo", usuario.getCorreo())
                .append("telegram", usuario.getIdTelegram())
                        .append("direccion", "1 calle zona 1");

        collection.insertOne(document);
        System.out.println("Usuario Creado");
    }

    public void leerUsuario(){
        List<mdUsuario> usuario = new ArrayList<>();

        //Leer todos los documentos
        for (Document doc: collection.find()){
            mdUsuario u = new mdUsuario();
            u.setIdTelegram(doc.getLong("id Telegram"));
            u.setNombre(doc.getString("nombre:"));
            u.setCorreo((doc.getString("Correo")));
            usuario.add(u);
        }
        for (mdUsuario u: usuarios){
            System.out.println("usuario"+u.getNombre(),"idtelegram"+u.getIdTelegram(),"correo"+u.getCorreo());


        }
        //actualiza un usuario en base a una condicion
        private  void actualizarUsuario() {
            collection.updateOne(Filters.eq("idTelegram", 88745587),
                    Updates.set("correo", "Nuevo@example.com"));
            System.out.println("Usuario actualizado");
        }

        private  void eliminarUsuario() {
            collection.deleteOne(Filters.eq("idTelegram", 9995587));
            System.out.println("Usuario eliminado");
        }

        private  void desconectarMongoDB() {
            // Cerrar la conexión
            mongoClient.close();
            System.out.println("Desconectado de MongoDB");
        }
        private  void conectarMongoDB() {
            // Conexión a MongoDB
            ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .build();
            mongoClient = MongoClients.create(settings);

            // Obtener la base de datos y la colección
            database = mongoClient.getDatabase("miBaseDeDatos");
            collection = database.getCollection("usuarios");
            System.out.println("Conectado a MongoDB");
        }



        //buscar usuario por correo
        //returna un registro
        private  void buscarUnUsuarioCorreo(String correo) {
            Document doc = collection.find(Filters.eq("correo", correo)).first();
            if (doc != null) {
                System.out.println("Usuario encontrado: " + doc.toJson());
            } else {
                System.out.println("Usuario no encontrado");
            }
        }
        //buscar usuario por correo
        //returna un registro
        private  void buscarUnUsuarioCorreo(String correo) {
            Document doc = collection.find(Filters.eq("correo", correo)).first();
            if (doc != null) {
                System.out.println("Usuario encontrado: " + doc.toJson());
            } else {
                System.out.println("Usuario no encontrado");
            }
        }




        //buscar usuario por correo
        //retorna una lista de registros
        private  List<mdUsuario> buscarUsuariosPorCorreo(String correo) {
            List<mdUsuario> usuarios = new ArrayList<>();
            MongoCursor<Document> cursor = collection.find(Filters.eq("correo", correo)).iterator();

            try {
                while (cursor.hasNext()) {
                    Document doc = cursor.next();
                    mdUsuario u = new mdUsuario();
                    u.setIdTelegram(doc.getLong("idTelegram"));
                    u.setNombre(doc.getString("nombre"));
                    u.setCorreo(doc.getString("correo"));
                    usuarios.add(u);
                }
            } finally {
                cursor.close();
            }

            return usuarios;
        }
    }

}// end class
