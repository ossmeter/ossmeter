package org.ossmeter.platform.bugtrackingsystem.bitbucket.api;

@Deprecated
public class BitbucketRepository {
	private String scm;
	private boolean has_wiki;
	private String last_updated;
	private String creator;
	private int forks_count;
	private String created_on;
	private String owner;
	private String logo;
	private String email_mailinglist;
	private boolean is_mq;
	private long size;
	private boolean read_only;
	private String fork_of;
	private String mq_of;
	private int followers_count;
	private String state;
	private String utc_created_on;
	private String website;
	private String description;
	private String has_issues;
	private boolean is_fork;
	private String slug;
	private boolean is_private;
	private String name;
	private String language;
	private String utc_last_updated;
	private boolean email_writers;
	private boolean no_public_forks;
	private String resource_uri;
	
	public BitbucketRepository() {
	}

	public String getScm() {
		return scm;
	}

	public void setScm(String scm) {
		this.scm = scm;
	}

	public boolean isHas_wiki() {
		return has_wiki;
	}

	public void setHas_wiki(boolean has_wiki) {
		this.has_wiki = has_wiki;
	}

	public String getLast_updated() {
		return last_updated;
	}

	public void setLast_updated(String last_updated) {
		this.last_updated = last_updated;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public int getForks_count() {
		return forks_count;
	}

	public void setForks_count(int forks_count) {
		this.forks_count = forks_count;
	}

	public String getCreated_on() {
		return created_on;
	}

	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getEmail_mailinglist() {
		return email_mailinglist;
	}

	public void setEmail_mailinglist(String email_mailinglist) {
		this.email_mailinglist = email_mailinglist;
	}

	public boolean isIs_mq() {
		return is_mq;
	}

	public void setIs_mq(boolean is_mq) {
		this.is_mq = is_mq;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public boolean isRead_only() {
		return read_only;
	}

	public void setRead_only(boolean read_only) {
		this.read_only = read_only;
	}

	public String getFork_of() {
		return fork_of;
	}

	public void setFork_of(String fork_of) {
		this.fork_of = fork_of;
	}

	public String getMq_of() {
		return mq_of;
	}

	public void setMq_of(String mq_of) {
		this.mq_of = mq_of;
	}

	public int getFollowers_count() {
		return followers_count;
	}

	public void setFollowers_count(int followers_count) {
		this.followers_count = followers_count;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUtc_created_on() {
		return utc_created_on;
	}

	public void setUtc_created_on(String utc_created_on) {
		this.utc_created_on = utc_created_on;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHas_issues() {
		return has_issues;
	}

	public void setHas_issues(String has_issues) {
		this.has_issues = has_issues;
	}

	public boolean isIs_fork() {
		return is_fork;
	}

	public void setIs_fork(boolean is_fork) {
		this.is_fork = is_fork;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public boolean isIs_private() {
		return is_private;
	}

	public void setIs_private(boolean is_private) {
		this.is_private = is_private;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}

	public String getUtc_last_updated() {
		return utc_last_updated;
	}

	public void setUtc_last_updated(String utc_last_updated) {
		this.utc_last_updated = utc_last_updated;
	}

	public boolean isEmail_writers() {
		return email_writers;
	}

	public void setEmail_writers(boolean email_writers) {
		this.email_writers = email_writers;
	}

	public boolean isNo_public_forks() {
		return no_public_forks;
	}

	public void setNo_public_forks(boolean no_public_forks) {
		this.no_public_forks = no_public_forks;
	}

	public String getResource_uri() {
		return resource_uri;
	}

	public void setResource_uri(String resource_uri) {
		this.resource_uri = resource_uri;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
