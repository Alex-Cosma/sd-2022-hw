const mapSerializer = (map: Map<any, any>): string => {
      const entries: [any, any][] = [...map];
      return Object.fromEntries(entries);
}

export default mapSerializer;