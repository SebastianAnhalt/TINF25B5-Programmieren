
```mermaid
classDiagram
%% 1. Define the Styles (one attribute per line to prevent parse errors)
classDef blueInterface fill:#e3edfa
classDef blueInterface stroke:#4a7ebb
classDef blueInterface stroke-width:2px

classDef greenClass fill:#e2eed8
classDef greenClass stroke:#76933c
classDef greenClass stroke-width:2px

%% 2. Interface Definition
class Collection {
<<Interface>>
+add(E e): Boolean
+size(): Integer
+iterator(): Iterator~E~
    }

%% 3. Class Definition
class ArrayList {
-elementData: Object[]
-size: Integer
+add(E e): Boolean
+size(): Integer
+iterator(): Iterator~E~
+ensureCapacity(minCapacity: Integer): void
}

%% 4. Assign Styles
class Collection blueInterface
class ArrayList greenClass

%% 5. Relationship
ArrayList ..|> Collection : implements
```
```mermaid
flowchart TD
    %% Define Styles
    classDef construct fill:#e1f5fe,stroke:#0288d1,stroke-width:2px
    classDef instantiation fill:#e8f5e9,stroke:#388e3c,stroke-width:2px
    classDef method fill:#fff3e0,stroke:#f57c00,stroke-width:2px

    %% Constructs
    Collection:::construct
    List:::construct
    Set:::construct
    SortedSet:::construct
    Map:::construct

    %% Instantiations
    ArrayList:::instantiation
    HashSet:::instantiation
    TreeSet:::instantiation
    HashMap:::instantiation

    %% Methods
    Collections:::method

    %% Relationships
    List --|> Collection
    Set --|> Collection
    SortedSet --|> Set
    
    ArrayList --|> List
    HashSet --|> Set
    TreeSet --|> SortedSet
    HashMap --|> Map
    
    Collections -.-> List
    Collections -.-> Set
    Collections -.-> Map
```

```mermaid
classDiagram
    %% Farb- und Style-Definitionen für die Artefakte
    style Construct fill:#e1f5fe,stroke:#0288d1,stroke-width:2px;
    style Instantiation fill:#e8f5e9,stroke:#388e3c,stroke-width:2px;
    style Method fill:#fff3e0,stroke:#f57c00,stroke-width:2px;

    %% --- ARTEFAKT: CONSTRUCTS (Schnittstellen) ---
    class Collection {
        <<Interface / Construct>>
        +add(E e) Boolean
        +contains(Object o) Boolean
    }
    style Collection Construct

    class List {
        <<Interface / Construct>>
        +get(int index) E
        +set(int index, E element) E
    }
    style List Construct

    class Set {
        <<Interface / Construct>>
    }
    style Set Construct

    class SortedSet {
        <<Interface / Construct>>
        +first() E
        +last() E
    }
    style SortedSet Construct

    class Map {
        <<Interface / Construct>>
        +put(K key, V value) V
        +get(Object key) V
        +keySet() Set~K~
        +values() Collection~V~
    }
    style Map Construct

    %% --- ARTEFAKT: INSTANTIATIONS (Konkrete Klassen) ---
    class ArrayList {
        <<Class / Instantiation>>
        -Object[] elementData
    }
    style ArrayList Instantiation

    class HashSet {
        <<Class / Instantiation>>
        -HashMap map
    }
    style HashSet Instantiation

    class TreeSet {
        <<Class / Instantiation>>
        -TreeMap tree
    }
    style TreeSet Instantiation

    class HashMap {
        <<Class / Instantiation>>
        -Node[] table
    }
    style HashMap Instantiation

    %% --- ARTEFAKT: METHODS (Hilfsfunktionen) ---
    class Collections {
        <<Utility Class / Method>>
        +sort(List list)$
        +shuffle(List list)$
        +unmodifiableSet(Set s)$ Set
        +emptyMap()$ Map
    }
    style Collections Method

    %% --- BEZIEHUNGEN (UML-Pfeile) ---
    %% Vererbung von Schnittstellen (Spezialisierung von Constructs)
    List --|> Collection : extends
    Set --|> Collection : extends
    SortedSet --|> Set : extends

    %% Realisierung von Schnittstellen (Instantiierung von Constructs)
    ArrayList ..|> List : implements
    HashSet ..|> Set : implements
    TreeSet ..|> SortedSet : implements
    HashMap ..|> Map : implements

    %% Abhängigkeiten der Hilfsmethoden (Methods operieren auf Constructs)
    Collections ..> List : <<uses>>
    Collections ..> Set : <<uses>>
    Collections ..> Map : <<uses>>

    %% Strukturelle Brücke von Map zu den anderen Constructs
    Map ..> Set : <<returns via keySet()>>
    Map ..> Collection : <<returns via values()>>
    ```
