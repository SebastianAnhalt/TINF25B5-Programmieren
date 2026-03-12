```mermaid
classDiagram
class JFrame {
<<Library Class>>
+setTitle(title: String)
+setVisible(b: boolean)
}

    class Pigeon {
        -JTextField userField
        -JPasswordField passwortField
        -JButton loginButton
        -String VALID_USER
        -char[] VALID_PASSWORD_HASH
        +Pigeon()
        -validateLogin()
    }

    class Arrays {
        <<Utility Class>>
        +equals(char[] a, char[] b) static
        +fill(char[] a, char val) static
    }

    class ActionListener {
        <<interface>>
        +actionPerformed(e: ActionEvent)
    }

    %% Beziehungen
    JFrame <|-- Pigeon : erbt von (Extends)
    Pigeon *-- JTextField : besitzt (Heap)
    Pigeon *-- JPasswordField : besitzt (Heap)
    Pigeon *-- JButton : besitzt (Heap)
    Pigeon ..> Arrays : nutzt für sicheren Vergleich
    Pigeon ..> ActionListener : implementiert Event-Logik
```

## Update of the model

```mermaid
classDiagram
    direction TB

    %% Existing Infrastructure
    class JFrame { <<Library Class>> }
    class Pigeon {
        -JTextField userField
        -JButton loginButton
        -ProjectsManager manager
        +Pigeon()
        -validateLogin()
        -initializeDashboard()
    }

    %% Core Logic & Management
    class ProjectsManager {
        -List~Project~ projects
        -ConfigLoader config
        +loadProjects(Path directory) List~Project~
        +syncAllToOEP() void
        +getProjectStatus(UUID id) Status
    }

    class Project {
        -UUID id
        -String title
        -Path roCratePath
        -ROCrateMetadata metadata
        +isValid() boolean
        +updateMetadata(ROCrateMetadata data) void
    }

    %% Metadata & Schema Domain
    class ROCrateMetadata {
        -JsonObject rawJson
        -String linkmlSource
        +getEntities() List~Entity~
        +validateAgainstSchema(Schema s) boolean
    }

    class LinkMLSchema {
        <<Interface>>
        +getClasses()
        +getSlots()
    }

    %% Integration & Transformation
    class CrosswalkManager {
        -MappingRules rules
        +transform(LinkMLSchema source, OEMetadata target) OEMetadata
        -mapFields(JsonNode sourceData) JsonNode
    }

    class OEMetadata {
        -String schemaVersion
        -JsonObject oepJsonStructure
        +toJsonString() String
        +validateForOEP() boolean
    }

    class OEPConnector {
        -HttpClient client
        -String apiKey
        +publish(OEMetadata data) HttpResponse
        +testConnection() boolean
    }

    %% Relationships
    JFrame <|-- Pigeon
    Pigeon *-- ProjectsManager : manages execution
    ProjectsManager "1" *-- "*" Project : contains
    Project "1" *-- "1" ROCrateMetadata : describes
    
    CrosswalkManager ..> LinkMLSchema : reads from
    CrosswalkManager ..> OEMetadata : produces
    
    ProjectsManager ..> CrosswalkManager : triggers transformation
    ProjectsManager ..> OEPConnector : triggers upload
    OEPConnector ..> OEMetadata : sends
```
## Delimitation of Model, View, Controller
```mermaid
graph TB
    subgraph View ["View (UI Layer)"]
        direction TB
        P[Pigeon JFrame]
        JF[JFrame Library]
        subgraph UI_Components ["Swing Elements"]
            B1[loginButton]
            B2[publishButton]
            T1[userField]
        end
    end

    subgraph Controller ["Controller (Logic Layer)"]
        direction TB
        PM[ProjectsManager]
        CM[CrosswalkManager]
        OC[OEPConnector]
        AL[ActionListener]
    end

    subgraph Model ["Model (Data Layer)"]
        direction TB
        PR[Project]
        RM[ROCrateMetadata]
        OM[OEMetadata]
        LS[LinkMLSchema]
    end

%% Interactions between boxes
    P -- notifies --> AL
    AL -- triggers --> PM
    PM -- manages --> PR
    PR -- holds --> RM
    PM -- uses --> CM
    CM -- maps LinkML to --> OM
    PM -- sends to OEP via --> OC
```
## Pigeon publication sequence
```mermaid
sequenceDiagram
    autonumber
    actor User as Researcher (User)
    participant V as View (Pigeon JFrame)
    participant C as Controller (ProjectsManager)
    participant M as Model (Project / RO-Crate)
    participant X as Crosswalk (LinkML to OEP)
    participant API as OEP API (Remote)

    Note over User, V: Phase 1: Import
    User->>V: Click "Import Projects"
    V->>C: loadProjects(directoryPath)
    C->>M: Instantiate Project(ro-crate-metadata.json)
    M-->>C: Project valid
    C-->>V: Update UI List (Show Project)

    Note over User, V: Phase 2: Metadata Mapping
    User->>V: Click "Publish"
    V->>C: syncAllToOEP()
    C->>M: getROCrateMetadata()
    C->>X: transform(LinkMLSource)
    X->>X: mapFields(LinkML -> OEMetadata)
    X-->>C: return OEMetadata object

    Note over User, V: Phase 3: Actual Publication
    C->>API: POST /datasets (OEMetadata JSON)
    API-->>C: 201 Created / Success
    C->>M: setStatus(PUBLISHED)
    C-->>V: Show Success Dialog
    V-->>User: Visual Confirmation
```
## Pigeon System snapshot
taken right after you have imported a research dataset from a folder on your Mac.
```mermaid
graph TD
%% Objects represented as boxes with "name : type"

    subgraph Instances ["Snapshot: Active Publication Task"]

        P["<u>mainApp : Pigeon</u><br/>userField = 'Researcher_KIT'<br/>status = 'Ready'"]

        PM["<u>manager : ProjectsManager</u><br/>activeThreads = 1"]

        PR["<u>dataset_01 : Project</u><br/>id = 550e8400-e29b<br/>path = '~/data/ro-crate-01'<br/>status = 'Pending'"]

        RM["<u>crateData : ROCrateMetadata</u><br/>title = 'Energy Flow 2026'<br/>license = 'CC-BY-4.0'"]

        CM["<u>xWalker : CrosswalkManager</u><br/>ruleset = 'v1.2_oemeta'"]

        OC["<u>connector : OEPConnector</u><br/>endpoint = 'api.openenergy-platform.org'"]

    end

%% Linkages (Instances of Associations)
    P --- PM
    PM --- PR
    PR --- RM
    PM --- CM
    PM --- OC
```
a second snapshot is during the active publication phase. At this point, the system is in high gear: the 
CrosswalkManager has finished its work, a new OEMetadata object has been instantiated in memory, and the 
OEPConnector is actively transmitting data.

```mermaid
graph TD
    %% Objects with "name : type" and active state values
    
    subgraph Active_Snapshot ["Snapshot: Mid-Publication (T + 45s)"]
        
        P["<u>mainApp : Pigeon</u><br/>status = 'Publishing...'"]
        
        PM["<u>manager : ProjectsManager</u><br/>activeThreads = 1"]
        
        PR["<u>dataset_01 : Project</u><br/>id = 550e8400-e29b<br/>status = 'UPLOADING'"]
        
        RM["<u>crateData : ROCrateMetadata</u><br/>source = 'LinkML'"]
        
        OM["<u>transferData : OEMetadata</u><br/>isValid = true<br/>schema = 'oemeta_v1.5'"]
        
        OC["<u>connector : OEPConnector</u><br/>currentTask = 'POST Request'<br/>bytesSent = 4096"]
        
    end

    %% Active relationships
    P --- PM
    PM --- PR
    PR --- RM
    PM --- OM
    PM --- OC
    OC -.-> API[("Open Energy Platform")]
```
The final snapshot represents the terminal state of a successful publication cycle. At this point, 
the transient transformation objects have been cleared from memory (garbage collected), the project 
status has updated to reflect its permanent presence on the OEP, and the UI has returned to an idle, 
"Ready" state for the next task.
```mermaid
graph TD
    %% Final state values
    
    subgraph Terminal_Snapshot ["Snapshot: Task Completed"]
        
        P["<u>mainApp : Pigeon</u><br/>status = 'Ready'<br/>lastMessage = 'Publication Successful'"]
        
        PM["<u>manager : ProjectsManager</u><br/>activeThreads = 0"]
        
        PR["<u>dataset_01 : Project</u><br/>id = 550e8400-e29b<br/>status = 'PUBLISHED'<br/>oepURL = 'https://openenergy-platform.org/data/...'"]
        
        RM["<u>crateData : ROCrateMetadata</u><br/>source = 'LinkML'"]
        
        OC["<u>connector : OEPConnector</u><br/>currentTask = 'Idle'<br/>lastResponseCode = 201"]
        
    end

    %% Remaining associations
    P --- PM
    PM --- PR
    PR --- RM
    PM --- OC

    %% Note on GC
    style Terminal_Snapshot fill:#f9f,stroke:#333,stroke-width:2px
    Note[The 'transferData : OEMetadata' object <br/>has been Garbage Collected]
```
