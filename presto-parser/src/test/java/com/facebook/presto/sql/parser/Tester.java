package com.facebook.presto.sql.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.facebook.presto.sql.tree.AliasedRelation;
import com.facebook.presto.sql.tree.Expression;
import com.facebook.presto.sql.tree.Node;
import com.facebook.presto.sql.tree.Query;
import com.facebook.presto.sql.tree.QuerySpecification;
import com.facebook.presto.sql.tree.Relation;
import com.facebook.presto.sql.tree.Select;
import com.facebook.presto.sql.tree.Statement;
import com.google.common.collect.TreeTraverser;
import com.google.gson.Gson;


public class Tester {
	static List<Node> set = new ArrayList<Node>();
	public static void main(String[] args) {
		SqlParser SQL_PARSER = new SqlParser();
		BufferedReader br = null;
		FileReader fr = null;
		String query="";
		try {
			fr = new FileReader("query1.sql");
			br = new BufferedReader(fr);
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				query+=sCurrentLine;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(query);
		Query stmt = (Query)SQL_PARSER.createStatement(query);
		//System.out.println(stmt);
		QuerySpecification body = (QuerySpecification)stmt.getQueryBody();
		//Gson gson = new Gson();
		//System.out.println(gson.toJson(body));
		Select select =  body.getSelect();
		//System.out.println("Columns = " + select.getSelectItems());
        System.out.println("From = " + body.getFrom().get());
        
        //extractTableNames(body.getFrom().get());
        Optional<Expression> where = body.getWhere();
        //System.out.println("Where = " + where.get());
        //System.out.println("Group by = " + body.getGroupBy());
        //System.out.println("Order by = " + body.getOrderBy());
        //System.out.println("Limit = " + body.getLimit().get());
        List<Node> items=recursiveExtract(stmt.getChildren());
        System.out.println(items);
		
	}
	
	public static void extractTableNames(Relation rel) {
		System.out.println(rel);
		
		List<Node> nodes = (List<Node>) rel.getChildren();
        for(Node node : nodes) {
        	System.out.println(node.getClass());
        	System.out.println("+++++++");
        	    /*List<Node> nodes1 = (List<Node>) rel.getChildren();
        	    for(Node node1 : nodes) {
        	    		if (node1 instanceof com.facebook.presto.sql.tree.AliasedRelation) {
        	    			AliasedRelation ar = (AliasedRelation)node1;
        	    			System.out.println(ar.getRelation());
        	    		}
        	    	   
        	    }*/
     }
	}
	
	
	
	public static List<Node> recursiveExtract(List<? extends Node> list) {  
		for(Node nd:list) {
			System.out.println(nd.getClass());
			if(nd instanceof com.facebook.presto.sql.tree.Table) {
				set.add((Node)nd);
			}
			if(nd instanceof com.facebook.presto.sql.tree.SingleColumn) {
				set.add((Node)nd);
				System.out.println("+++adding+++"+set.size());
			}
			else if(nd instanceof Node) {
				recursiveExtract(((Node) nd).getChildren());
			}
		}
	    return set;
	}
}
