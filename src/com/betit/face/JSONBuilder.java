package com.betit.face;


import com.betit.entities.Answer;
import com.google.gson.Gson;




public class JSONBuilder {
		static Gson gson = new Gson();
		public static void main(String[] args) {
			Answer aw = new Answer(new Long(23245), new Long(23123134),"HI");
			createJSON(aw);
		}
//		public JSONBuilder(){
//			
//		}
		//Generate fom Single Entity
		public static void createJSON(Object o){
		
			//gson = new Gson();
		}
		
		public void createJSON(Object[]o){
			
		}
}
