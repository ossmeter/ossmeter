package org.ossmeter.platform.communicationchannel.zendesk;


import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.communicationchannel.zendesk.model.Forum;
import org.ossmeter.platform.communicationchannel.zendesk.model.Ticket;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelDelta;

public class ZendapiTest {

	public void test() {
		Zendesk zendesk;
//		zendesk = new org.ossmeter.platform.communicationchannel.zendesk.Zendesk.Builder("https://support.opensourcebim.org")
//			.build();
		zendesk = new Zendesk.Builder("https://univaq.zendesk.com")
		.setUsername("juri.dirocco@univaq.it").setPassword("Pavone84").build();
		Iterable<Forum> recentTicket = zendesk.getForums();
		for (Iterator<Forum> tiketIter = recentTicket.iterator(); tiketIter.hasNext();){
			Forum g = tiketIter.next();
			System.out.println("c s");
			System.out.println(g.getUnansweredTopics());
		    
		}
	}
	private final static int RETRIEVAL_STEP = 50;
	@Test
	public void test1() {
		Date date = new Date();
		//date = date.addDays(-2);
		org.ossmeter.repository.model.cc.zendesk.Zendesk communicationChannel = 
				new org.ossmeter.repository.model.cc.zendesk.Zendesk();
		communicationChannel.setUsername("juri.dirocco@univaq.it");
		communicationChannel.setPassword("Pavone84");
		communicationChannel.setUrl("https://univaq.zendesk.com");
		
		org.ossmeter.platform.communicationchannel.zendesk.Zendesk zendesk;
		zendesk = new org.ossmeter.platform.communicationchannel.zendesk.Zendesk.Builder(
				communicationChannel.getUrl())
				.setUsername(communicationChannel.getUsername())
				.setPassword(communicationChannel.getPassword()).build();
		Iterable<Ticket> recentTickets = zendesk.getRecentTickets();
		Ticket lastTicket = recentTickets.iterator().next();
		CommunicationChannelDelta delta = new CommunicationChannelDelta();
		delta.setNewsgroup(communicationChannel);
		if (lastTicket != null) {
			long lastTicketId = lastTicket.getId();
			String lac = communicationChannel.getLastArticleChecked();
			if (lac == null || lac.equals("") || lac.equals("null"))
				lac = "1";
			long lastTicketChecked = Long.parseLong(lac);

			long retrievalStep = RETRIEVAL_STEP;
			Boolean dayCompleted = false;
			while (!dayCompleted) {
				if (lastTicketChecked + retrievalStep > lastTicketId) {
					retrievalStep = lastTicketId - lastTicketChecked;
					dayCompleted = true;
				}
				List<Ticket> tickets = null;
				Date articleDate = date;
				// The following loop discards messages for days earlier than
				// the required one.
				do {
					tickets = zendesk.getTickets(lastTicketChecked, lastTicketChecked+ retrievalStep);
					if (tickets.size() > 0) {
						Ticket lastArticleRetrieved = tickets.get(tickets.size()-1);
						java.util.Date javaArticleDate = lastArticleRetrieved.getUpdatedAt();
						articleDate = new Date(javaArticleDate);
						date = articleDate;
						if (date.compareTo(articleDate) > 0)
							lastTicketChecked = lastArticleRetrieved.getId();
					}
				} while (date.compareTo(articleDate) > 0);
				for (Ticket tk: tickets) {
					java.util.Date javaTicketDate = tk.getUpdatedAt();
					if (javaTicketDate!=null) {
						articleDate = new Date(javaTicketDate);
						if (date.compareTo(articleDate) < 0) {
							dayCompleted = true;
//							System.out.println("dayCompleted");
						}
						else if (date.compareTo(articleDate) == 0) {
							CommunicationChannelArticle communicationChannelArticle = new CommunicationChannelArticle();
							communicationChannelArticle.setArticleId(tk.getId() + "");
							int i = Integer.parseInt(tk.getId()+"");
							communicationChannelArticle.setArticleNumber(i);
							communicationChannelArticle.setDate(javaTicketDate);
//							I haven't seen any messageThreadIds on NNTP servers, yet.
//							communicationChannelArticle.setMessageThreadId(article.messageThreadId());
							org.ossmeter.repository.model.cc.zendesk.Zendesk zenDesk = 
									new org.ossmeter.repository.model.cc.zendesk.Zendesk();
							zenDesk.setUrl(communicationChannel.getUrl());
							zenDesk.setAuthenticationRequired(communicationChannel.getAuthenticationRequired());
							zenDesk.setUsername(communicationChannel.getUsername());
							zenDesk.setPassword(communicationChannel.getPassword());
							zenDesk.setNewsGroupName(communicationChannel.getNewsGroupName());
							zenDesk.setPort(communicationChannel.getPort());
							zenDesk.setInterval(communicationChannel.getInterval());
							//communicationChannelArticle.setNewsgroup(zenDesk);
							//communicationChannelArticle.setReferences(article.getReferences());
							communicationChannelArticle.setSubject(tk.getSubject());
							
							
							communicationChannelArticle.setUser(zendesk.getUser(tk.getRequesterId()).getName());
							
							communicationChannelArticle.setText(tk.getDescription());
							delta.getArticles().add(communicationChannelArticle);
							lastTicketChecked = tk.getId();
//							System.out.println("dayNOTCompleted");
						} 
						else {

								//TODO: In this case, there are unprocessed articles whose date is earlier than the date requested.
								//      This means that the deltas of those article dates are incomplete, 
								//		i.e. the deltas did not contain all articles of those dates.
						}
					} else {
						// If an article has no correct date, then ignore it
						System.err.println("\t\tUnparsable article date: " + tk.getUpdatedAt());
					}
				}
				
			}
			communicationChannel.setLastArticleChecked(lastTicketChecked+"");
		
		}
		zendesk.close(); 
		System.out.println("delta ("+date.toString()+") contains:\t"+
								delta.getArticles().size() + " nntp articles");
	}

}

