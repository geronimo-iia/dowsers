


 Copyright 2012 Jerome Guibert

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.





References

http://cre8ivethought.com/blog/2009/11/20/cqrs-domain-events


This event store can be based on an object database, document database, file system or even a RDBMS, you basically need to have one collection that describes all the different objects that the event store has persisted this includes the Id and the Version. Then another collection will contain all the serialized events for each different object and they should be retrievable by the object Id ordered by their Version. So to simplify this, in a RDBMS this would mean 2 tables, in total.
https://github.com/MarkNijhof/Fohjin/tree/master/Fohjin.DDD.Example/


http://cre8ivethought.com/blog/2009/11/20/cqrs-domain-events
https://github.com/MarkNijhof/Fohjin/blob/master/Fohjin.DDD/Fohjin.EventStore/Fohjin.EventStore/Infrastructure/IEventProvider.cs

http://cre8ivethought.com/blog/2009/11/28/cqrs-trying-to-make-it-re-usable

http://cre8ivethought.com/blog/2009/12/08/cqrs-domain-state

http://cre8ivethought.com/blog/2009/12/22/specifications

http://cre8ivethought.com/blog/2010/02/05/cqrs-event-sourcing

http://cre8ivethought.com/blog/2010/02/09/cqrs-event-versioning

http://cre8ivethought.com/blog/2010/02/09/cqrs-scalability

http://cre8ivethought.com/blog/2009/11/20/cqrs-domain-events




https://raw.github.com/ncqrs/ncqrs/master/Extensions/src/Ncqrs.Eventing.Storage.SqlLite/Query.cs
https://raw.github.com/ncqrs/ncqrs/master/Extensions/src/Ncqrs.Eventing.Storage.SqlLite/Query.cs





Reference:
* Domain-Driven Design and Command-Query Separation example application https://github.com/erikrozendaal/cqrs-lottery,  Erik Rozendaal, Xebia
* From http://martinfowler.com/:
	Event Sourcing: http://martinfowler.com/eaaDev/EventSourcing.html
	CQRS
* 
* http://www.axonframework.org/
* DDD book
* DDD 
* Blog
* Date4j http://www.date4j.net/ http://clojars.org/date4j on git

