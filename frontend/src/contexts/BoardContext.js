import React from "react";

export const BoardContext = React.createContext({
    color: {
        r: 0,
        g: 0,
        b: 0,
        a: 255,
    },
    setColor: () => {},
});