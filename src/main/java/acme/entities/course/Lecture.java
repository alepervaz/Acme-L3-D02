
package acme.entities.course;

import javax.persistence.Entity;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Lecture extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	protected static final long serialVersionUID = 1L;

}
