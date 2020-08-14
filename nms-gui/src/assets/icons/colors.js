export function getStatusColor (status) {
  if (status === 'UP') {
    return '#30bf6c'
  } else if (status === 'DOWN') {
    return '#ff5349'
  } else {
    return '#ffc200'
  }
}

export function getStatusClass (status) {
  if (status === 'UP') {
    return 'row-up'
  } else if (status === 'DOWN') {
    return 'row-down'
  }
  return 'row-disconn'
}

export function getAvailableClass (status) {
  if (status === true) {
    return 'row-up'
  }
  return 'row-down'
}

export function getBusyColor (busy) {
  if (busy === true) {
    return 'info'
  }
  return 'gray'
}
