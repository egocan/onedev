package com.pmease.gitplex.web.page;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.model.LoadableDetachableModel;

import com.pmease.commons.hibernate.dao.Dao;
import com.pmease.gitplex.core.GitPlex;
import com.pmease.gitplex.core.model.Repository;
import com.pmease.gitplex.web.component.sourceview.Source;
import com.pmease.gitplex.web.component.sourceview.SourceViewPanel;

@SuppressWarnings("serial")
public class TestPage extends BasePage {

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		/*
		add(new Link<Void>("testIndex") {

			@Override
			public void onClick() {
				Repository repo = GitPlex.getInstance(Dao.class).load(Repository.class, 2L);
				IndexManager indexManager = GitPlex.getInstance(IndexManager.class);
				Date since;
				try {
					since = new SimpleDateFormat("yyyy-MM-dd").parse("2010-01-01");
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
				
				long time = System.currentTimeMillis();
				List<Commit> commits = repo.git().log(since, null, null, 0, 0);
				System.out.println("Total commits to index: " + commits.size());
				int count = 0;
				for (Commit commit: commits) {
					System.out.println("" + (count++) + ": " + commit.getCommitter().getWhen());
					indexManager.index(repo, commit.getHash());			
				}
				System.out.println("Total Minutes: " + (System.currentTimeMillis()-time)/1000/60);
			}
			
		});
		
		add(new Link<Void>("testSearch") {

			@Override
			public void onClick() {
				Repository repo = GitPlex.getInstance(Dao.class).load(Repository.class, 1L);
				SearchManager searchManager = GitPlex.getInstance(SearchManager.class);
				try {
					long time = System.currentTimeMillis();
					String commitHash = repo.git().parseRevision("master~10", true);
					BlobQuery query = new TextQuery("windows", true, false, false, null, null, 1000);
					List<QueryHit> hits = searchManager.search(repo, commitHash, query);
					System.out.println(hits.size());
					System.out.println(System.currentTimeMillis()-time);
				} catch (InterruptedException e) {
				}
			}
			
		});
		
		add(new WebMarkupContainer("searchResult").setOutputMarkupId(true));
		
		add(new BlobSearchPanel("searcher", new LoadableDetachableModel<Repository>() {

			@Override
			protected Repository load() {
				return GitPlex.getInstance(Dao.class).load(Repository.class, 2L);
			}
			
		}, Model.of("master")) {

			@Override
			protected void onSelect(AjaxRequestTarget target, QueryHit hit) {
				System.out.println(hit.getBlobPath() + ": " + hit.getLineNo());
			}

			@Override
			protected void onCompleteAdvancedSearch(AjaxRequestTarget target, List<QueryHit> hits) {
				BlobAdvancedSearchResultPanel searchResult = new BlobAdvancedSearchResultPanel("searchResult", hits) {

					@Override
					protected void onSelect(AjaxRequestTarget target, QueryHit hit) {
						System.out.println(hit.getBlobPath() + ": " + hit.getLineNo());
					}
					
				};
				searchResult.setOutputMarkupId(true);
				getPage().get("searchResult").replaceWith(searchResult);
				target.add(searchResult);
			}
			
		});
		*/
		
		add(new SourceViewPanel("sourceView", new LoadableDetachableModel<Repository>() {

			@Override
			protected Repository load() {
				return GitPlex.getInstance(Dao.class).load(Repository.class, 2L);
			}
			
		}, new LoadableDetachableModel<Source>() {

			@Override
			protected Source load() {
				/*
				Repository repository = GitPlex.getInstance(Dao.class).load(Repository.class, 2L);
				org.eclipse.jgit.lib.Repository jgitRepo = repository.openAsJGitRepo();
				try {
					RevTree revTree = new RevWalk(jgitRepo).parseCommit(repository.resolveRevision("master")).getTree();
					TreeWalk treeWalk = TreeWalk.forPath(jgitRepo, 
							"gitplex.web/src/main/java/com/pmease/gitplex/web/component/search/BlobSearchPanel.java", 
							revTree);
					ObjectLoader objectLoader = jgitRepo.open(treeWalk.getObjectId(0));
					String content = new String(objectLoader.getCachedBytes());
					return new Source("master", treeWalk.getPathString(), content);
				} catch (IOException e) {
					throw new RuntimeException(e);
				} finally {
					jgitRepo.close();
				}
				*/
				try {
					String content = FileUtils.readFileToString(new File("w:\\temp\\Component.java"));
					return new Source("master", "Component.java", content);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			
		}));
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
	}

}
