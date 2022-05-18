package gr.devian.owl.project.models;

public enum Role {
    SCREENWRITER("Screenwriter", "ont:hasWrittenMovie"),
    PRODUCER("Producer", "ont:hasProducedMovie"),
    DIRECTOR("Director", "ont:hasDirectedMovie"),
    ACTOR("Actor", "ont:wasLeadInMovie|ont:wasExtraInMovie|ont:wasSupportInMovie"),
    STAFF("Staff", "ont:wasStaffInMovie");

    final String friendlyName;
    final String ontologyRelationship;

    Role(final String friendlyName, final String ontologyRelationship) {
        this.friendlyName = friendlyName;
        this.ontologyRelationship = ontologyRelationship;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }

    public String getOntologyRelationship() {
        return this.ontologyRelationship;
    }

    @Override
    public String toString() {
        return this.friendlyName;
    }
}
