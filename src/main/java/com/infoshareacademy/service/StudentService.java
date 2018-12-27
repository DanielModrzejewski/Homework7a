package com.infoshareacademy.service;


import com.infoshareacademy.dao.ComputerDao;
import com.infoshareacademy.dao.StudentDao;
import com.infoshareacademy.model.Computer;
import com.infoshareacademy.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
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

    @Inject
    private ComputerDao computerDao;

    public StudentService() {
    }

    @GET
    @Path("/students")
    @Produces(MediaType.TEXT_PLAIN)
    public Response showAllStudents() {
        String resp = new String();
        final List<Student> result = studentDao.findAll();
        LOG.info("Found {} objects", result.size());
        for (Student s : result) {
            resp+=(s.toString() + "\n");
        }
        return Response.ok(resp).build();
    }

    @GET
    @Path("/students/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getStudentsByName(@PathParam("name") String n) {
        String resp = new String();
        final List<Student> result = studentDao.findByName(n);
        LOG.info("Found {} objects", result.size());
        for (Student s : result) {
            resp+=(s.toString() + "\n");
        }
        return Response.ok(resp).build();
    }

    @POST
    @Path("/computers")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addComputer(Computer computer) {
        computerDao.save(computer);
        String resp = new String();
        final List<Computer> result = computerDao.findAll();
        LOG.info("Found {} objects", result.size());
        for (Computer c : result) {
            resp+=(c.toString() + "\n");
        }
        return Response.ok(resp).build();
    }

    @DELETE
    @Path("/computers/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteComputerById(@PathParam("id") String id) {
        if (computerDao.findById(Long.valueOf(id))==null) {
            LOG.info("No found computer wih id: ", id);
            return Response.status(404).build();
        }
        LOG.info("Computer id: {} deleted", id);
        computerDao.delete(Long.valueOf(id));
        return Response.status(200).build();
    }
}
