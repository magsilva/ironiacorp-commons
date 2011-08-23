/*
Copyright (C) 2011 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/


package com.ironiacorp.patterns.observer;

import com.ironiacorp.patterns.Pattern;


/**
 * Intent: Define a one-to-many dependency between objects so that when one object
 * changes state, all its dependents are notified and updated automatically.
 * 
 * Motivation: The need to maintain consistency between related objects without making
 * classes tightly coupled. 
 * 
 * Applicability: Use the Observer pattern in any of the following situations:
 * <ul>
 * 	<li>When an abstraction has two aspects, one dependent on the other.</li>
 * 	<li>Encapsulating these aspects in separate objects lets you vary and reuse
 * 	them independently.</li>
 * 	<li>When a change to one object requires changing others</li>
 * 	<li>When an object should be able to notify other objects without making
 * assumptions about those objects</li>
 * </ul>
 * 
 * Benefits:
 * <ul>
 * 	<li>Minimal coupling between the Subject and the Observer
 * 	<ul>
 * 		<li>Can reuse subjects without reusing their observers and vice versa</li>
 * 		<li>Observers can be added without modifying the subject</li>
 * 		<li>All subject knows is its list of observers</li>
 * 		<li>Subject does not need to know the concrete class of an observer, just that
 * 		each observer implements the update interface.</li>
 * 		<li>Subject and observer can belong to different abstraction layers</li>
 * 	</ul>
 * 	<li>Support for event broadcasting
 * 	<ul>
 * 		<li>Subject sends notification to all subscribed observers</li>
 * 		<li>Observers can be added/removed at any time</li>
 * 	</ul>
 * 
 * Liabilities
 * <ul>
 * 	<li>Possible cascading of notifications
 * 	<ul>
 * 		<li>Observers are not necessarily aware of each other and must be careful
 * 		about triggering updates</li>
 * 	</ul>
 * 	<li>Simple update interface requires observers to deduce changed item</li>
 * </ul>
 */
public interface ObserverPattern extends Pattern
{
}
