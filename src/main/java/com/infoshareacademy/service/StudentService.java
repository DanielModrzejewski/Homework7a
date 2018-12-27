package com.infoshareacademy.service;


import com.infoshareacademy.dao.StudentDao;
import com.infoshareacademy.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Transactional
@Path("/api/")
public class StudentService {

    private Logger LOG = LoggerFactory.getLogger(StudentService.class);

    @Context
    private UriInfo uriInfo;

    @Inject
    private StudentDao studentDao;

    public StudentService() {
    }

    @GET
    @Path("/students")
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayHello() {
        String resp = new String();
        final List<Student> result = studentDao.findAll();
        LOG.info("Found {} objects", result.size());
        for (Student p : result) {
            resp+=(p.toString() + "\n");
        }
        return Response.ok(resp).build();
    }

    @GET
    @Path("/students/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayHello(@PathParam("name") String n) {
        String resp = new String();
        final List<Student> result = studentDao.findByName(n);
        LOG.info("Found {} objects", result.size());
        for (Student p : result) {
            resp+=(p.toString() + "\n");
        }
        return Response.ok(resp).build();
    }
}
