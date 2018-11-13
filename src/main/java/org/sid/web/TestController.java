package org.sid.web;

import java.sql.SQLException;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class TestController {
	
	@Autowired
	private DataSource dataSource;
	
	@RequestMapping("/testshit")
	private String testshit() {
		
		try {
			dataSource.getConnection().createStatement().executeUpdate("insert into users_roles values ('shitshit','roleshitrole')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "blank";
	}

}
