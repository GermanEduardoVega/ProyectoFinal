   mermaid.live
   

``` classDiagram
    %% MODULO DE IDENTIDAD Y ROLES
    class User {
    +Long id
    +String username
    +Role role
    +Boolean isSubscribedToNewsletter
    }
    class Role {
    <<enumeration>>
    PATIENT
    PROFESSIONAL
    CLINIC_ADMIN
    }

    %% MODULO DE SALUD (GRATUITO PARA EL PACIENTE)
    class AnxietyRecord {
        +Long id
        +LocalDateTime timestamp
        +String rawInput
        +Integer anxietyLevel
        +String trigger
        +AI_Response aiResponse
    }
    class AI_Response {
        +String technique
        +String awarenessMessage
        +List~String~ actionSteps
    }

    %% MODULO DE CLINICA Y AUTOMATIZACION (EL CLIENTE QUE PAGA)
    class Clinic {
        +Long id
        +String name
        +PlanType activePlan
    }
    class AgentConfig {
        +Boolean autoResponder
        +String welcomeMessage
        +String triageLogic
    }
    class Appointment {
        +LocalDateTime dateTime
        +Status status
    }

    %% MODULO DE FINANZAS (EL MOTOR DEL NEGOCIO)
    class Subscription {
        +Long id
        +PlanType plan
        +Double price
        +LocalDateTime nextBilling
    }
    class PlanType {
        <<enumeration>>
        START
        PLUS
        PRO
    }
    class Referral {
        +Long id
        +LocalDateTime date
        +Double commissionRate
    }

    %% RELACIONES
    User "1" -- "*" AnxietyRecord : "registra gratis"
    AnxietyRecord "1" -- "1" AI_Response : "genera"
    
    Clinic "1" -- "1" AgentConfig : "configura"
    Clinic "1" -- "1" Subscription : "paga"
    
    Patient "1" -- "*" Appointment : "agenda"
    Professional "1" -- "*" Appointment : "atiende"
    
    Clinic "1" -- "*" User : "emplea profesionales"
    Referral "*" -- "1" Clinic : "deriva paciente a"
    Referral "*" -- "1" User : "proviene de"