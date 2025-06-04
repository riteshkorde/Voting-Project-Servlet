package com.sunbeam.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.sunbeam.entities.Candidate;
import com.sunbeam.daos.CandidateDao;
import com.sunbeam.daos.CandidateDaoImpl;
import com.sunbeam.entities.Candidate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/vote")
public class Vote extends HttpServlet  {
	


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
	  doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	    String selectedCandidateId = req.getParameter("candidate");

        // Case 1: User just opened the vote page → show list
        if (selectedCandidateId == null) {
		List<Candidate> list=null;
		
		try(CandidateDao candDao = new CandidateDaoImpl()) {
			list = candDao.findAll();
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Candidates</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h3>Candidate List</h3>");
		out.println("<form method='post' action='vote'>");
		// <input type='radio' name='candidate' value='id?'/> candname?
		for(Candidate c:list)
			out.printf("<input type='radio' name='candidate' value='%d'/> %s - %s <br/>\n", c.getId(), c.getName(), c.getParty());
		out.println("<input type='submit' value='Vote'/>");
		out.println("</form>");		
		out.println("</body>");
		out.println("</html>");
		
        }else {
        	try(CandidateDao cand =new CandidateDaoImpl()){
            	int id=Integer.parseInt(selectedCandidateId);
            	cand.incrVote(id);
            	
      		    resp.setContentType("text/html");
                PrintWriter out = resp.getWriter();
                out.println("<html>");
                out.println("<head><title>Vote Submitted</title></head>");
                out.println("<body>");
                out.printf("<h3>Thank you for voting! You voted for Candidate ID: %d</h3>", id);
        		out.println("<div><button><a href='logout'>Sign Out</a></button></div>");

                out.println("<a href='index.html'>Back to Home</a>");
                out.println("</body>");
                out.println("</html>");
        	}catch(Exception e) {
        		e.printStackTrace();
        		}
        	  
        }
	
		
		
	}
	
	

}
