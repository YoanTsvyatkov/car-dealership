import React, { useContext } from 'react'
import { useLocalStorage } from '../custom-hooks/use-storage';

const UserContext = React.createContext()

const DUMMY_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpdmFuMSIsImV4cCI6MTY1NTU3Mzk1MiwiaWF0IjoxNjU1NTYzMTUyfQ.1_PdXTRCEtIrO3u91QSgXVKVBkLYVA6E955nXarQpckqPW8lSUNTnpmew8yuau0ZvoCAyTiVfwo4eKQa5mVe8w"

export function useUserContext() {
  return useContext(UserContext)
}

export function UserProvider({ children }) {
    //TODO remove dummy token
  const [token, setToken, remove] = useLocalStorage('AUTH_TOKEN', DUMMY_TOKEN)

  return (
    <UserContext.Provider value={{ token, setToken, remove}}>
      {children}
    </UserContext.Provider>
  )
}