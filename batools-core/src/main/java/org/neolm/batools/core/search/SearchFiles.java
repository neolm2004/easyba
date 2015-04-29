package org.neolm.batools.core.search;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.neolm.batools.core.inspect.FileEntry;
import org.neolm.batools.core.inspect.FileEntryConstant;
import org.neolm.batools.core.repository.RepositoryFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 搜索主要处理类
 * 
 * @author neolm
 * */

public class SearchFiles {

	private static Logger logger = Logger.getLogger(SearchFiles.class);

	private IndexSearcher indexSearcher;
	
	private IndexReader indexReader ;

	private QueryParser parser;
	
	private Analyzer analyzer ;

	public ArrayList<FileEntry> doSearch(String keyword)   {
		ArrayList<FileEntry> result = new ArrayList<FileEntry>();
		BufferedReader in = null;
		try {
			

			parser.setDefaultOperator(QueryParser.AND_OPERATOR);
			Query query = parser.parse(keyword);
			
			// -- termquery-start

			
			// -- termquery-end			
			
			
			
			logger.debug(query);
			//TopDocs results = indexSearcher.search(query, 150);
			TopDocs results = indexSearcher.search(query, null, 400);
			ScoreDoc[] hits = results.scoreDocs;
			
			int numTotalHits = results.totalHits;
			logger.debug("keyword:"+keyword+"; totalhis:"+numTotalHits);
			
			//higherIndex( query, results);
			
			if(numTotalHits>0){				
				for(int i =0 ;i<hits.length;i++){
					FileEntry fe = new FileEntry();
					Document doc = indexSearcher.doc(hits[i].doc);
					
					// highlighter -start-
					SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("[#hl]", "[/#hl]");    
		            Highlighter highlighter = new Highlighter(simpleHTMLFormatter,new QueryScorer(query));    
		           
		            
		            // 标题
		            TokenStream tokenStream1 = analyzer.tokenStream(FileEntryConstant.FIELD_PATH, new StringReader(doc.get(FileEntryConstant.FIELD_PATH)));
		            String title = highlighter.getBestFragment(tokenStream1, doc.get(FileEntryConstant.FIELD_PATH));
		            // 内容
		            TokenStream tokenStream2 = analyzer.tokenStream(FileEntryConstant.FIELD_CONTENTS, new StringReader(doc.get(FileEntryConstant.FIELD_CONTENTS)));
		            String content = highlighter.getBestFragment(tokenStream2, doc.get(FileEntryConstant.FIELD_CONTENTS));
		            logger.debug(doc.get(FileEntryConstant.FIELD_PATH) + " : " + title + " : " + content);

					// highlighter -end-
					
					fe.setFilePath(doc.get(FileEntryConstant.FIELD_PATH));
					fe.setFieName(title==null||title.length()==0?doc.get(FileEntryConstant.FIELD_PATH):title);
					fe.setDescription(content);
					fe.setAuthor(doc.get(FileEntryConstant.FIELD_AUTHOR));
					fe.setRevision(doc.get(FileEntryConstant.FIELD_REVISION)!=null?Long.parseLong(doc.get(FileEntryConstant.FIELD_REVISION)):0);
										
					// 加入结果
					result.add(fe);
				}
				
			}
			
			return result ;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error("Lucene ParseException :",e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Lucene IOException :",e);
		} catch (InvalidTokenOffsetsException e) {
			// TODO Auto-generated catch block
			logger.error("Lucene InvalidTokenOffsetsException :",e);
		} finally{
			
		}
		return result;

		
	}
	
	public void higherIndex( Query query, TopDocs topDocs)
            throws IOException, InvalidTokenOffsetsException {
        TopScoreDocCollector results = TopScoreDocCollector.create(topDocs.totalHits, false);
        indexSearcher.search(query, results);
        // 分页取doc(开始条数, 取几条)
        ScoreDoc[] docs = results.topDocs(1, 2).scoreDocs;
        for (int i = 0; i < docs.length; i++) {
            Document targetDoc = indexSearcher.doc(docs[i].doc);
            //logger.debug("内容：" + targetDoc.toString());
        }
        // 关键字高亮显示的html标签
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
        for (int i = 0; i < docs.length; i++) {
            Document doc = indexSearcher.doc(docs[i].doc);
            // 标题增加高亮显示
            TokenStream tokenStream1 = analyzer.tokenStream(FileEntryConstant.FIELD_PATH, new StringReader(doc.get(FileEntryConstant.FIELD_PATH)));
            String title = highlighter.getBestFragment(tokenStream1, doc.get(FileEntryConstant.FIELD_PATH));
            // 内容增加高亮显示
            TokenStream tokenStream2 = analyzer.tokenStream(FileEntryConstant.FIELD_CONTENTS, new StringReader(doc.get(FileEntryConstant.FIELD_CONTENTS)));
            String content = highlighter.getBestFragment(tokenStream2, doc.get(FileEntryConstant.FIELD_CONTENTS));
           
        }
    }

	public IndexSearcher getIndexSearcher() {
		return indexSearcher;
	}

	public void setIndexSearcher(IndexSearcher indexSearcher) {
		this.indexSearcher = indexSearcher;
	}

	public QueryParser getParser() {
		return parser;
	}

	public void setParser(QueryParser parser) {
		this.parser = parser;
	}

	public IndexReader getIndexReader() {
		return indexReader;
	}

	public void setIndexReader(IndexReader indexReader) {
		this.indexReader = indexReader;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"classpath*:org/neolm/batools/core/spring/applicationContext-*.xml");
		SearchFiles sf = (SearchFiles) ac.getBean("searchFiles");

		logger.info(sf.doSearch("无线DDN"));
		
		//logger.info(sf.doSearch("4G"));
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

}
