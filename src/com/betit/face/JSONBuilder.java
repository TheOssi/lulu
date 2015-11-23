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
		public static void createJSON(Object o){
			String json = gson.toJson(o);
			
		}
}
