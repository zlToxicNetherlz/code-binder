package co.edu.eafit.code.binder.api;

import co.edu.eafit.code.generator.metamodel.arduino.classes.Project;
import co.edu.eafit.code.generator.metamodel.arduino.restrictions.exceptions.ArduinoRestrictionException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.stream.Collectors;

public class TestServer extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        System.out.println("[Petición] [GET] Se recibió una petición desde " + req.getRemoteAddr());
        doPost(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        System.out.println("[Petición] [POST] Se recibió una petición desde " + req.getRemoteAddr());

        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With, observe");
        resp.setHeader("Access-Control-Allow-Credentials", "true");

        resp.addHeader("Access-Control-Expose-Headers", "Authorization");
        resp.addHeader("Access-Control-Expose-Headers", "responseType");
        resp.addHeader("Access-Control-Expose-Headers", "observe");

        PrintWriter out = resp.getWriter();
        String basicJson = req.getReader().lines().collect(Collectors.joining());
        String response;

        if (basicJson == null || basicJson.isEmpty()) {

            out.print("No se envió ningún tipo de información.");
            return;

        }

        try {

            Project project = BinderAPI.getDynamicProject(basicJson);
            response = project.getBoards().get(0).generateCode().getBufferCode().toString();

        } catch (Exception e) {

            if (e instanceof ArduinoRestrictionException)
                response = "Error de restricción: ";
            else
                response = "Ha ocurrido un error durante el binding: ";

            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));

            response += errors.toString();

        }

        out.print(response);

    }
}