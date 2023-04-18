/*
 * AuthenticatedConsumerController.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.any.peep;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.peep.Peep;
import acme.framework.components.accounts.Any;
import acme.framework.controllers.AbstractController;

@Controller
public class AnyPeepController extends AbstractController<Any, Peep> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyPeepCreateService	peepCreateService;
	@Autowired
	protected AnyPeepListService	peepListService;
	@Autowired
	protected AnyPeepShowService	peepShowService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.peepCreateService);
		super.addBasicCommand("list", this.peepListService);
		super.addBasicCommand("show", this.peepShowService);

	}

}
