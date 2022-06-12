import React, { useContext } from 'react'
import { useLocalStorage } from '../custom-hooks/use-storage';

const UserContext = React.createContext()

const DUMMY_TOKEN = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnb3NobyIsImV4cCI6MTY1NTA3MTU2NSwiaWF0IjoxNjU1MDYwNzY1fQ.0KQxcHCa6zBqL5f00bZepyLSqtmGggoAwUV6T1vn5kXRvqmjSxnFKoR9hvEXDS3LgJyZr7o-9JpsC90i8YNPfw'

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