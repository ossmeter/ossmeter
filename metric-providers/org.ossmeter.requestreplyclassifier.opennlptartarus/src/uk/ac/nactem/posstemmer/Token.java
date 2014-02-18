package uk.ac.nactem.posstemmer;

public class Token {
    private String surface;
    private String norm;
    private String pos;

    public Token(String surface, String norm, String pos) {
        this.surface = surface;
        this.norm = norm;
        this.pos = pos;
    }

    public String getSurfaceForm() {
        return surface;
    }

    public String getNormalForm() {
        return norm;
    }

    public void setNormalForm(String normalForm) {
		norm = normalForm;
	}

	public String getPoS() {
        return pos;
    }

	public void setPoS(String pos) {
		this.pos = pos;
	}
	
    @Override
    public String toString() {
        return surface + "\t" + norm + "\t" + pos;
    }

}
