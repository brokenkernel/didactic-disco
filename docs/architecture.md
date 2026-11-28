# Navigation

- Screen — A detailed set of information for the application to show. Contains all metadata required to display.
- Route — A specific name of a Screen available to the public API.
- Tab — The top level Composable displayed by a given Screen

# Module Graph

```mermaid
%%{
  init: {
    'theme': 'neutral'
  }
}%%

graph LR
  :app["app"]
  :components["components"]
  :coreinfra["coreinfra"]
  :features["features"]
  :coreinfra["coreinfra"]
  :components["components"]
  :app["app"]
  :coreinfra["coreinfra"]
  subgraph :features
    :features:debug["debug"]
    :features:homescreen["homescreen"]
    :features:spectrogram["spectrogram"]
    subgraph :debug
      :features:debug:api["api"]
      :features:debug:impl["impl"]
      :features:debug:impl["impl"]
      :features:debug:api["api"]
      :features:debug:api["api"]
      :features:debug:impl["impl"]
    end
    subgraph :homescreen
      :features:homescreen:api["api"]
      :features:homescreen:impl["impl"]
      :features:homescreen:api["api"]
      :features:homescreen:impl["impl"]
      :features:homescreen:impl["impl"]
      :features:homescreen:api["api"]
    end
    subgraph :spectrogram
      :features:spectrogram:api["api"]
      :features:spectrogram:impl["impl"]
      :features:spectrogram:api["api"]
      :features:spectrogram:api["api"]
      :features:spectrogram:impl["impl"]
      :features:spectrogram:impl["impl"]
      :features:spectrogram:api["api"]
    end
  end

  : --> :app
  : --> :components
  : --> :coreinfra
  : --> :features
  : --> :features:debug
  : --> :features:homescreen
  : --> :features:spectrogram
  : --> :features:debug:api
  : --> :features:debug:impl
  : --> :features:homescreen:api
  : --> :features:homescreen:impl
  : --> :features:spectrogram:api
  : --> :features:spectrogram:impl
  :features:debug:impl --> :coreinfra
  :features:debug:impl --> :components
  :features:debug:impl --> :features:debug:api
  :features:spectrogram:api --> :coreinfra
  :features:debug:api --> :coreinfra
  :app --> :components
  :app --> :coreinfra
  :app --> :features:debug:api
  :app --> :features:debug:impl
  :app --> :features:homescreen:api
  :app --> :features:homescreen:impl
  :app --> :features:spectrogram:api
  :app --> :features:spectrogram:impl
  :features:homescreen:impl --> :coreinfra
  :features:homescreen:impl --> :features:homescreen:api
  :features:homescreen:impl --> :features:spectrogram:api
  :features:spectrogram:impl --> :coreinfra
  :features:spectrogram:impl --> :features:spectrogram:api
  :features:homescreen:api --> :coreinfra

classDef unknown fill:#676767,stroke:#fff,stroke-width:2px,color:#fff;
classDef android-application fill:#2C4162,stroke:#fff,stroke-width:2px,color:#fff;
classDef android-library fill:#3BD482,stroke:#fff,stroke-width:2px,color:#fff;
class : unknown
class :app android-application
class :components android-library
class :coreinfra android-library
class :features unknown
class :features:debug unknown
class :features:homescreen unknown
class :features:spectrogram unknown
class :features:debug:api android-library
class :features:debug:impl android-library
class :features:homescreen:api android-library
class :features:homescreen:impl android-library
class :features:spectrogram:api android-library
class :features:spectrogram:impl android-library

```