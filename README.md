[![No Maintenance Intended](http://unmaintained.tech/badge.svg)](http://unmaintained.tech/)


If you find this interesting you might want to check out:

* [Bastardised kanban](https://speakerdeck.com/chids/bastardised-kanban), a presentation on the subject from 2015
* http://github.com/plan3/stoneboard/, a slightly less outdated, Github powered, take on this

---

Roadmap
=======

Text based road maps of things that are either "not started", "in progress" or "done", and may have inter-dependencies, 
rendered as dot

I like visualizing stuff. This is a quick hack prototype of how to visualize "road maps". Groups of stuff that are to be
done in some sort of sequence and might be interdependent such that Foo must be done in order for us to do thing Bar.  

## Example

```yaml
name: "Project Name"
sections:
    - section:
        name: dev:development
        items:
            - java6:x
            - maven:x
            - jenkins
            - java7
            - sonar
    - section:
        name: infra:infrastructure
        items:
            - scp:x
            - ansible
                -> dev:maven
                -> meta:deft
            - chef
    - section:
        name: meta:process
        items:
            - roadmap:-
            - deft
            - jira
```

![Example roadmap](https://raw.github.com/chids/roadmap/master/sample.png)

## Acknowledgements

----

>> Dropwizard
>> 
>> Copyright 2011-2012 [Coda Hale](https://github.com/codahale) and Yammer, Inc.
>> 
>> This product includes software developed by Coda Hale and Yammer, Inc.

----

>> Graphviz-Api
>> 
>> Copyright [Kohsuke Kawaguchi](https://github.com/kohsuke)

----
