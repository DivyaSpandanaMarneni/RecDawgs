package edu.uga.cs.recdawgs.presentation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.cs.recdawgs.RDException;
import edu.uga.cs.recdawgs.entity.League;
import edu.uga.cs.recdawgs.entity.Team;
//import edu.uga.cs.recdawgs.logic.LogicLayer;
import edu.uga.cs.recdawgs.session.Session;
import edu.uga.cs.recdawgs.session.SessionManager;
import edu.uga.cs.recdwags.logic.LogicLayer;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Servlet implementation class SearchLeague
 */
@WebServlet("/SearchLeague")
public class SearchLeague extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static String templateDir = "WEB-INF/templates";
    static String resultTemplateName = "SearchLeague-Result.ftl";

    private Configuration cfg; 

    public void init() 
    {
        cfg = new Configuration();
        cfg.setServletContextForTemplateLoading(getServletContext(), "WEB-INF/templates");
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchLeague() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		Template resultTemplate = null;
		BufferedWriter toClient = null;
        LogicLayer logicLayer = null;
        League leagueObject = null;
        League league  = null;
        HttpSession httpSession;
        Session session;
        String ssid;
		
        String leagueName = request.getParameter("txtLeagueName");
       
        
		  try {
		      resultTemplate = cfg.getTemplate( resultTemplateName );
		  } 
		  catch( IOException e ) {
		      throw new ServletException("Can't load template in: " + templateDir + ": " + e.toString());
		  }
		  
		  toClient = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
			//response.setContentType("text/html; charset=" +resultTemplate.getEncoding());
			
			httpSession = request.getSession();
			if(httpSession == null) { //not logged in session expired
				//ClubsError.error( cfg, toClient, "Session expired or illegal; please log in" );
				System.out.println("No session  for the user");
		          return;

			}
			
			ssid = (String) httpSession.getAttribute("ssid");
			if(ssid == null){
				//not logged in
				System.out.println("Not logged in");
				return;
			}
			session = SessionManager.getSessionById(ssid);
			if(session == null){
				//show error
				System.out.println("Session expired, not logged ");
				return;
			}
			
			 logicLayer = session.getLogicLayer();
			 List<List<Object>>  leagues= new LinkedList<List<Object>>();
		     List<Object>        league1= null;
		        
			Map<String,Object> root = new HashMap<String,Object>();
			root.put( "leagues",leagues);
			 
			  try {
				  
			      league = logicLayer.searchLeague(leagueName);

			      // Build the data-model
			      //
			      league1=new LinkedList<Object>();
			      System.out.println("leaguename"+league.getName());
			      league1.add(league.getName());
			      league1.add(league.getId());
			      league1.add(league.getLeagueRules());
			      league1.add(league.getMatchRules());
			      boolean isIndoor=false;
			      if(league.getIsIndoor()){
			    	  league1.add("Indoor");
				      
			      }//if
			      else{
			    	  league1.add("Outdoor");
				      
			      }//else
			      league1.add(league.getMinTeams());
			      league1.add(league.getMaxTeams());
			      league1.add(league.getMinMembers());
			      league1.add(league.getMaxMembers());
			      leagues.add(league1);
			      //leagueMap.put("league_id", (Long) league.getId());
			      //leagueMap.put("league_rules", league.getLeagueRules());
			     // leagueMap.put("match_rules", league.getMatchRules());
			      //leagueMap.put("isIndoor", (Boolean.toString(league.getIsIndoor())));
			     // leagueMap.put("min_teams", league.getMinTeams());
			      //leagueMap.put("max_teams", league.getMaxTeams());
			      //leagueMap.put("min_members", league.getMinMembers());
			      //leagueMap.put("max_members", league.getMaxMembers());

			  } 
			  catch( Exception e) {
//			      ClubsError.error( cfg, toClient, e );
//			      return;
			  }

			  try {
			      resultTemplate.process(root, toClient );
			      toClient.flush();
			  } 
			  catch (Exception e) {
			      throw new ServletException( "Error while processing FreeMarker template", e);
			  }

			  toClient.close();
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
	}

}
